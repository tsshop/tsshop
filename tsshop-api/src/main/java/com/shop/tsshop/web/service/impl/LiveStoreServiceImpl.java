package com.shop.tsshop.web.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.common.utils.StringUtils;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.mapper.*;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.domain.param.CashOutConfigParams;
import com.shop.tsshop.web.model.domain.pay.AlipayParams;
import com.shop.tsshop.web.model.domain.pay.PayTypeVo;
import com.shop.tsshop.web.model.dto.*;
import com.shop.tsshop.web.model.vo.AmountTypeVo;
import com.shop.tsshop.web.model.vo.LiveStoreDetialVo;
import com.shop.tsshop.web.model.vo.LiveStoreGoodsVo;
import com.shop.tsshop.web.model.vo.OrderDetailVo;
import com.shop.tsshop.web.service.PayThoroughfareService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.service.LiveStoreService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName LiveStoreServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
@Slf4j
public class LiveStoreServiceImpl extends ServiceImpl<LiveStoreMapper, LiveStore> implements LiveStoreService{

    @Autowired
    LiveStoreMapper liveStoreMapper;

    @Autowired
    LiveStoreGoodsMapper liveStoreGoodsMapper;

    @Autowired
    LiveStoreGoodsSkuMapper liveStoreGoodsSkuMapper;

    @Autowired
    LiveStoreApplyMapper liveStoreApplyMapper;

    @Autowired
    PayThoroughfareService payThoroughfareService;

    @Autowired
    GoodsSkuMapper goodsSkuMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    LiveStoreWithdrawalsAccountMapper liveStoreWithdrawalsAccountMapper;

    @Autowired
    LiveStoreWithdrawalsConfigMapper liveStoreWithdrawalsConfigMapper;

    @Autowired
    LiveStoreCashOutLogMapper liveStoreCashOutLogMapper;

    @Autowired
    LiveStoreWithdrawalsLogMapper liveStoreWithdrawalsLogMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ApiResult<Object> getInfo(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStoreApply liveStoreApply = liveStoreApplyMapper.selectOne(new LambdaQueryWrapper<LiveStoreApply>()
                .eq(LiveStoreApply::getUserId, user.getId()).eq(LiveStoreApply::getAuditStatus,1));
        if (ObjectUtil.isNull(liveStoreApply)){
            LiveStoreApply liveStoreApplying = liveStoreApplyMapper.selectOne(new LambdaQueryWrapper<LiveStoreApply>()
                    .eq(LiveStoreApply::getUserId, user.getId()).eq(LiveStoreApply::getAuditStatus,0));
            if (ObjectUtil.isNotNull(liveStoreApplying)){
                LiveStoreDetialVo liveStoreDetialVo = new LiveStoreDetialVo();
                liveStoreDetialVo.setApplyFlag(true);
                liveStoreDetialVo.setApplyStatus(liveStoreApplying.getAuditStatus());
                return ApiResult.ok(liveStoreDetialVo);
            }
            LiveStoreApply liveStoreApplyed = liveStoreApplyMapper.selectList(new LambdaQueryWrapper<LiveStoreApply>()
                    .eq(LiveStoreApply::getUserId, user.getId()).eq(LiveStoreApply::getAuditStatus,2)
                    .orderByDesc(LiveStoreApply::getCreateTime)).stream().findFirst().orElse(null);
            if (ObjectUtil.isNotEmpty(liveStoreApplyed)){
                LiveStoreDetialVo liveStoreDetialVo = new LiveStoreDetialVo();
                liveStoreDetialVo.setApplyFlag(true);
                liveStoreDetialVo.setFailReason(liveStoreApplyed.getRemark());
                liveStoreDetialVo.setApplyStatus(liveStoreApplyed.getAuditStatus());
                return ApiResult.ok(liveStoreDetialVo);
            }
            return ApiResult.ok(new LiveStoreDetialVo(false,null,null,null));
        }
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>()
                .eq(LiveStore::getUserId,user.getId())
                .eq(LiveStore::getDeleted,NumberEnmus.ZERO.getNumber()));
        return ApiResult.ok(new LiveStoreDetialVo(true,null,liveStoreApply.getAuditStatus(),liveStore));
    }


    @Override
    public ApiResult<Object> liveStoreEdit(LiveStore liveStore, HttpServletRequest request) {
        if (!Validator.isMobile(liveStore.getStorePhone())){
            return ApiResult.fail("请输入正确的手机号");
        }
        User user = redisService.getCurrentUser(request);
        LiveStore userLiveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        if (ObjectUtil.isNull(userLiveStore)){
            return ApiResult.fail("参数异常");
        }
        BeanUtil.copyProperties(liveStore,userLiveStore, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        liveStoreMapper.updateById(userLiveStore);
        return ApiResult.ok();
    }

    @Override
    public ApiResult<Object> liveStoreOrderList(LiveStoreOrderListDto dto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        PageHelper.startPage(dto.getPageNumber(),dto.getPageSize());
        List<OrderDetailVo> orderDetailVos = orderMapper.selectListByLiveStoreId(dto, liveStore.getId());
        PageInfo<OrderDetailVo> pageInfo = new PageInfo<>(orderDetailVos);
        return ApiResult.ok(pageInfo);
    }

    @Override
    public ApiResult<Object> withdrawDepositType() {
        Object obj = redisService.getObj("pay:updateTime");
        if (ObjectUtil.isNotEmpty(obj)){
            Long updateTime = (Long) obj;
            if (!PayUtil.updateTime.equals(updateTime)){
                payThoroughfareService.initializationParams();
                PayUtil.updateTime = updateTime;
            }
        }
        List<PayTypeVo> payTypeVos = new ArrayList<>();
        for (PayTypeVo payTypeVo : PayUtil.payTypeVos) {
            if (payTypeVo.getType().equals(PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode())){
                payTypeVos.add(payTypeVo);
            }
        }
        return ApiResult.ok(payTypeVos);
    }

    @Override
    public ApiResult<Object> getWithdrawCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(80, 40, 4, 5);
        //得到code
        User user = redisService.getCurrentUser(request);
        redisService.saveCode("user:WithdrawCode:" + user.getId(),captcha.getCode(), 60L);
        String imageBase64 = captcha.getImageBase64();
        return ApiResult.ok(imageBase64);
    }

    @Override
    public void addProfit(Long liveStoreId, BigDecimal profit) {
        baseMapper.addProfit(liveStoreId,profit);
    }

    @Override
    public ApiResult<Object> liveStoreCashOut(LiveStoreCashOutDto dto, HttpServletRequest request) throws AlipayApiException {
        log.info("提现请求参数[{}]",dto);
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息有误，请刷新重试~");
        }
        String cashOutConfig = redisService.getString("config:cash_out");
        if (StringUtils.isEmpty(cashOutConfig)){
            return ApiResult.fail("配置信息有误，请刷新重试~");
        }
        CashOutConfigParams cashOutConfigParams = JSONUtil.toBean(cashOutConfig, CashOutConfigParams.class);
        LiveStoreWithdrawalsAccount liveStoreWithdrawalsAccount = liveStoreWithdrawalsAccountMapper
                .selectOne(new LambdaQueryWrapper<LiveStoreWithdrawalsAccount>()
                .eq(LiveStoreWithdrawalsAccount::getId, dto.getWithdrawalsAccountId())
                .eq(LiveStoreWithdrawalsAccount::getDeleted, false));
        if (ObjectUtil.isNull(liveStoreWithdrawalsAccount)){
            return ApiResult.fail("账户信息有误，请刷新重试");
        }

        String validate = validate(cashOutConfigParams, dto, liveStore);
        if (validate != null){
            return ApiResult.fail(validate);
        }
        log.info(">>>>>>>>>>>>>>>>> 校验通过");
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(dto.getPayThoroughfareId());
        if(PayEnums.ProviderEnum.ALI_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            return aliCashOut(payThoroughfare,dto,liveStore,liveStoreWithdrawalsAccount,cashOutConfigParams);
        }

        return ApiResult.fail("提现方式错误，请刷新重试");
    }

    /**
     * 支付宝提现
     * @param payThoroughfare                        支付通道参数
     * @param dto                                    dto
     * @param liveStore                              小店信息
     * @param account                                小店提现账户
     * @return                                       统一返回
     */
    private ApiResult<Object> aliCashOut(PayThoroughfare payThoroughfare, LiveStoreCashOutDto dto, LiveStore liveStore, LiveStoreWithdrawalsAccount account,CashOutConfigParams config) throws AlipayApiException {
        // 计算提现金额
        BigDecimal withdrawalCharge = dto.getCashWithdrawalAmount().multiply(new BigDecimal(config.getWithdrawRate())).setScale(2, RoundingMode.HALF_UP);
        BigDecimal withdrawalAmt = dto.getCashWithdrawalAmount().subtract(withdrawalCharge);

        liveStore.setAmt(liveStore.getAmt().subtract(dto.getCashWithdrawalAmount()));
        if (liveStoreMapper.updateById(liveStore) != 1){
            throw new MyException("系统异常: 提现失败");
        }
        LiveStoreWithdrawalsLog liveStoreWithdrawalsLog = new LiveStoreWithdrawalsLog();
        liveStoreWithdrawalsLog.setLiveStoreId(liveStore.getId());
        liveStoreWithdrawalsLog.setAmt(dto.getCashWithdrawalAmount());
        liveStoreWithdrawalsLog.setFees(withdrawalCharge);
        liveStoreWithdrawalsLog.setWithdrawalsType(dto.getPayType());
        liveStoreWithdrawalsLog.setRealName(account.getFullName());
        liveStoreWithdrawalsLog.setAccount(account.getAccountNumber());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String outBizNo = "out"+snowflake.nextId()+"";
        liveStoreWithdrawalsLog.setSerialNumber(outBizNo);
        AlipayFundTransUniTransferResponse response = transferAccount(withdrawalAmt,payThoroughfare,account,outBizNo);
        if (response.isSuccess()){
            log.info("提现成功");
            liveStoreWithdrawalsLog.setStatus(false);
            liveStoreWithdrawalsLogMapper.insert(liveStoreWithdrawalsLog);
            LiveStoreCashOutLog liveStoreCashOutLog = new LiveStoreCashOutLog();
            BeanUtil.copyProperties(response,liveStoreCashOutLog);
            liveStoreCashOutLogMapper.insert(liveStoreCashOutLog);
            return ApiResult.ok();
        }
        log.info("提现失败");
        liveStore.setAmt(liveStore.getAmt().add(dto.getCashWithdrawalAmount()));
        liveStoreMapper.updateById(liveStore);
        liveStoreWithdrawalsLog.setStatus(true);
        liveStoreWithdrawalsLog.setFailReason(response.getSubMsg());
        LiveStoreCashOutLog liveStoreCashOutLog = new LiveStoreCashOutLog();
        BeanUtil.copyProperties(response,liveStoreCashOutLog);

        liveStoreCashOutLog.setErrorCode(response.getCode());
        liveStoreCashOutLog.setErrorReason(response.getSubMsg());
        liveStoreCashOutLogMapper.insert(liveStoreCashOutLog);
        liveStoreWithdrawalsLogMapper.insert(liveStoreWithdrawalsLog);
        return ApiResult.fail(response.getSubMsg());
    }

    /**
     * 转账
     * @param withdrawalAmt                        转账金额
     * @param payThoroughfare                      支付宝参数
     * @param account                              账户信息
     * @return                                     response
     */
    private AlipayFundTransUniTransferResponse transferAccount(BigDecimal withdrawalAmt, PayThoroughfare payThoroughfare, LiveStoreWithdrawalsAccount account,String outBizNo) throws AlipayApiException {
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo(outBizNo);
        model.setRemark("单笔转账");
        model.setBizScene("DIRECT_TRANSFER");

        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity(account.getAccountNumber());
        payeeInfo.setIdentityType("ALIPAY_LOGON_ID");
        payeeInfo.setName(account.getFullName());

        model.setPayeeInfo(payeeInfo);
        model.setTransAmount(withdrawalAmt.toString());
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle("提现到账");
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = payThoroughfare.getAlipayParams().getAlipayClient().certificateExecute(request);
        log.info("提现response："+response.getBody());
        return response;
    }


    public String validate(CashOutConfigParams config,LiveStoreCashOutDto dto,LiveStore liveStore) {
        String msg = null;
        BigDecimal minWithdrawAmt = config.getMinWithdrawAmt();
        BigDecimal maxWithdrawAmt = config.getMaxWithdrawAmt();
        if (!Objects.equals(maxWithdrawAmt, new BigDecimal(0))){
            if (dto.getCashWithdrawalAmount().compareTo(minWithdrawAmt) <= 0 && dto.getCashWithdrawalAmount().compareTo(maxWithdrawAmt) >= 0) {
                return msg = "提现金额限制： " + minWithdrawAmt + " ~ " + maxWithdrawAmt;
            }
        }
        if (dto.getCashWithdrawalAmount().compareTo(minWithdrawAmt) < 0) {
            return msg = "最小提现金额：" + minWithdrawAmt;
        }
        if (config.getAllowableDecimal()){
            if (dto.getCashWithdrawalAmount().scale() > 0) {
                return msg = "系统提现只允许为整数";
            }
        }
        if (config.getWithdrawPeriod() == 0 && config.getWithdrawNum() != 0){
            Integer cashOutNum = liveStoreWithdrawalsLogMapper.selectCount(new LambdaQueryWrapper<LiveStoreWithdrawalsLog>()
                    .between(LiveStoreWithdrawalsLog::getCreateTime, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date())));
            if (cashOutNum >= config.getWithdrawNum()){
                return msg = "已达到每日提现限制: " + config.getWithdrawNum() + "次";
            }
        }
        if (config.getWithdrawPeriod() == 1 && config.getWithdrawNum() != 0){
            Integer cashOutNum = liveStoreWithdrawalsLogMapper.selectCount(new LambdaQueryWrapper<LiveStoreWithdrawalsLog>()
                    .between(LiveStoreWithdrawalsLog::getCreateTime, DateUtil.beginOfWeek(DateUtil.date()), DateUtil.endOfWeek(DateUtil.date())));
            if (cashOutNum >= config.getWithdrawNum()){
                return msg = "已达到每日提现限制: " + config.getWithdrawNum() + "次";
            }
        }
        if (config.getWithdrawPeriod() == 2 && config.getWithdrawNum() != 0){
            Integer cashOutNum = liveStoreWithdrawalsLogMapper.selectCount(new LambdaQueryWrapper<LiveStoreWithdrawalsLog>()
                    .between(LiveStoreWithdrawalsLog::getCreateTime, DateUtil.beginOfMonth(DateUtil.date()), DateUtil.endOfMonth(DateUtil.date())));
            if (cashOutNum >= config.getWithdrawNum()){
                return msg = "已达到每日提现限制: " + config.getWithdrawNum() + "次";
            }
        }
        return msg;
    }

}

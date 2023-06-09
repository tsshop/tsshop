package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.shop.tsshop.web.enums.TransportChargeType;
import com.shop.tsshop.web.model.domain.Area;
import com.shop.tsshop.web.model.domain.Transfee;
import com.shop.tsshop.web.model.domain.TransfeeFree;
import com.shop.tsshop.web.model.domain.Transport;
import com.shop.tsshop.web.service.TransportManagerService;
import com.shop.tsshop.web.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TransportManagerServiceImpl implements TransportManagerService {

    @Autowired
    private TransportService transportService;

    @Override
    public BigDecimal calculateTransfee(BigDecimal allMoney, Integer purchaseQuantity, Long deliveryTemplateId, Long cityId) {


        if (ObjectUtils.isEmpty(deliveryTemplateId)) {
            return new BigDecimal(0);
        }
        //找出该产品的运费项
        Transport transport = transportService.getTransportAndAllItems(deliveryTemplateId);
        //商家把运费模板删除
        if (transport == null) {
            return new BigDecimal(0);
        }

        // 用于计算运费的件数
        BigDecimal piece = new BigDecimal(purchaseQuantity);

        //如果有包邮的条件
        if (transport.getHasFreeCondition() == 1) {
            // 获取所有的包邮条件
            List<TransfeeFree> transfeeFrees = transport.getTransfeeFrees();
            for (TransfeeFree transfeeFree : transfeeFrees) {
                List<Area> freeCityList = transfeeFree.getFreeCityList();
                for (Area freeCity : freeCityList) {
                    if (!Objects.equals(freeCity.getAreaId(), cityId)) {
                        continue;
                    } else {

                        if (Objects.equals(TransportChargeType.COUNT.value(), transport.getChargeType())) {
                            // 按件数计算运费
                            piece = new BigDecimal(purchaseQuantity);
                        } else if (Objects.equals(TransportChargeType.WEIGHT.value(), transport.getChargeType())) {
                            // 按重量计算运费
                            BigDecimal weight = transfeeFree.getPiece() == null ? new BigDecimal(0) : transfeeFree.getPiece();
                            piece = weight.multiply(new BigDecimal(purchaseQuantity));
                        } else if (Objects.equals(TransportChargeType.VOLUME.value(), transport.getChargeType())) {
                            // 按体积计算运费
                            BigDecimal volume = transfeeFree.getPiece() == null ? new BigDecimal(0) : transfeeFree.getPiece();
                            piece = volume.multiply(new BigDecimal(purchaseQuantity));
                        }
                    }
                    //包邮方式 （0 满x件/重量/体积包邮 1满金额包邮 2满x件/重量/体积且满金额包邮）
                    if ((transfeeFree.getFreeType() == 0 && piece.compareTo(transfeeFree.getPiece()) >= 0) ||
                            (transfeeFree.getFreeType() == 1 && (allMoney.multiply(new BigDecimal(purchaseQuantity)).compareTo(transfeeFree.getAmount()) >= 0)) ||
                            (transfeeFree.getFreeType() == 2 && piece.compareTo(transfeeFree.getPiece()) >= 0 && (allMoney.multiply(new BigDecimal(purchaseQuantity)).compareTo(transfeeFree.getAmount()) >= 0))) {
                        return new BigDecimal(0);
                    }
                }
            }
        }

        //订单的运费项
        Transfee transfee = null;
        List<Transfee> transfees = transport.getTransfees();
        for (Transfee dbTransfee : transfees) {
            // 将该商品的运费设置为默认运费
            if (transfee == null && CollectionUtil.isEmpty(dbTransfee.getCityList())) {
                transfee = dbTransfee;
            }
            // 如果在运费模板中的城市找到该商品的运费，则将该商品由默认运费设置为该城市的运费
            for (Area area : dbTransfee.getCityList()) {
                if (area.getAreaId().equals(cityId)) {
                    transfee = dbTransfee;
                    break;
                }
            }
            // 如果在运费模板中的城市找到该商品的运费，则退出整个循环
            if (transfee != null && CollectionUtil.isNotEmpty(transfee.getCityList())) {
                break;
            }
        }

        // 如果无法获取到任何运费相关信息，则返回0运费
        if (transfee == null) {
            return new BigDecimal(0);
        }

        // 产品的运费
        BigDecimal fee = transfee.getFirstFee();
        // 如果件数大于首件数量，则开始计算超出的运费
        if (piece.compareTo(transfee.getFirstPiece()) > 0) {
            // 续件数量
            BigDecimal prodContinuousPiece = piece.subtract(transfee.getFirstPiece());
            // 续件数量的倍数，向上取整
            Integer mulNumber = prodContinuousPiece.divide(transfee.getContinuousPiece()).intValue();
            // 续件数量运费
            BigDecimal continuousFee = transfee.getContinuousFee().multiply(new BigDecimal(mulNumber));
            fee = fee.add(continuousFee);
        }
        return fee;
    }


}

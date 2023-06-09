package com.ts.shop.controller;

import com.ts.common.core.controller.BaseController;
import com.ts.common.core.page.TableDataInfo;
import com.ts.shop.domain.Transport;
import com.ts.shop.service.TransportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.ts.common.utils.PageUtils.startPage;

/**
 * @author lgh on 2018/11/16.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/shop/transport")
public class TransportController extends BaseController {

    private final TransportService transportService;


    /**
     * 查询运费模板列表
     */
    @PreAuthorize("@ss.hasPermi('shop:transport:list')")
    @GetMapping("/list")
    public TableDataInfo list(Transport transport)
    {
        startPage();
        List<Transport> list = transportService.selectTransportList(transport);
        return getDataTable(list);
    }

    /**
     * 获取信息
     */
    @PreAuthorize("@ss.hasPermi('shop:transport:list')")
    @GetMapping("/info/{id}")
    public ResponseEntity<Transport> info(@PathVariable("id") Long id) {
        Transport transport = transportService.getTransportAndAllItems(id);
        return ResponseEntity.ok(transport);
    }

    /**
     * 保存
     */
    @PreAuthorize("@ss.hasPermi('shop:transport:add')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Transport transport) {
        Date createTime = new Date();
        transport.setCreateTime(createTime);
        transportService.insertTransportAndTransfee(transport);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('shop:transport:edit')")
    public ResponseEntity<Void> update(@RequestBody Transport transport) {
        transportService.updateTransportAndTransfee(transport);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     */
    @DeleteMapping
    @PreAuthorize("@ss.hasPermi('shop:transport:del')")
    public ResponseEntity<Void> delete(@RequestBody Long[] ids) {
        transportService.deleteTransportAndTransfeeAndTranscity(ids);
        // 删除运费模板的缓存
        for (Long id : ids) {
            transportService.removeTransportAndAllItemsCache(id);
        }
        return ResponseEntity.ok().build();
    }


//    /**
//     * 获取运费模板列表
//     */
//    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('shop','transport:list')")
//    public ResponseEntity<List<Transport>> list() {
//        List<Transport> list = transportService.list();
//        return ResponseEntity.ok(list);
//    }

}

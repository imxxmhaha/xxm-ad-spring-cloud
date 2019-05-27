package cn.xxm.ad.search.controller;

import cn.xxm.ad.api.client.SponsorClient;
import cn.xxm.ad.api.entity.AdPlan;
import cn.xxm.ad.common.vo.AdPlanGetRequest;
import cn.xxm.ad.common.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author xxm
 * @create 2019-05-27 20:24
 */
@Slf4j
@RestController
public class SearchController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SponsorClient sponsorClient;

    @SuppressWarnings("all")
    //@IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByRibbon ->{}", JSON.toJSONString(request));
        CommonResponse body = restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan", request, CommonResponse.class).getBody();
        return body;
    }

    @SuppressWarnings("all")
    //@IgnoreResponseAdvice
    @PostMapping("/getAdPlansByFeign")
    public CommonResponse<List<AdPlan>> getAdPlansByFeign(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByFeign ->{}", JSON.toJSONString(request));
        CommonResponse<List<AdPlan>> adPlans = sponsorClient.getAdPlans(request);
        return adPlans;
    }
}

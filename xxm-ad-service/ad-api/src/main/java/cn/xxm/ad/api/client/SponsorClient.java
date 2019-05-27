package cn.xxm.ad.api.client;

import cn.xxm.ad.api.entity.AdPlan;
import cn.xxm.ad.common.vo.AdPlanGetRequest;
import cn.xxm.ad.common.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xxm
 * @create 2019-05-27 21:17
 */
@FeignClient(value = "EUREKA-CLIENT-AD-SPONSOR", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @PostMapping("/ad-sponsor/get/adPlan")
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}

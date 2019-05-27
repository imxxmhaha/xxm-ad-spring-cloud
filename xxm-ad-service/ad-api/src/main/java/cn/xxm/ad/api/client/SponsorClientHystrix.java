package cn.xxm.ad.api.client;

import cn.xxm.ad.api.entity.AdPlan;
import cn.xxm.ad.common.vo.AdPlanGetRequest;
import cn.xxm.ad.common.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xxm
 * @create 2019-05-27 22:02
 */
@Component
public class SponsorClientHystrix implements SponsorClient{

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>("-1","eureka-client-ad-sponsor error");
    }
}

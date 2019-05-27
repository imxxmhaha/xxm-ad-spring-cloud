package cn.xxm.ad.sponsor.service;


import cn.xxm.ad.common.vo.CreativeResponse;
import cn.xxm.ad.sponsor.vo.CreativeRequest;

/**
 * Created by Qinyi.
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);
}

package cn.xxm.ad.sponsor.service;


import cn.xxm.ad.sponsor.vo.CreativeRequest;
import cn.xxm.ad.sponsor.vo.CreativeResponse;

/**
 * Created by Qinyi.
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);
}

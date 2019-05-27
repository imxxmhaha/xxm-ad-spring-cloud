package cn.xxm.ad.sponsor.service.impl;

import cn.xxm.ad.api.entity.Creative;
import cn.xxm.ad.common.vo.CreativeResponse;
import cn.xxm.ad.sponsor.dao.CreativeRepository;
import cn.xxm.ad.sponsor.service.ICreativeService;
import cn.xxm.ad.sponsor.vo.CreativeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Qinyi.
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {

        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}

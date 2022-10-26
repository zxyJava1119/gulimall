package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", key).or().like("name", key);
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    /*
    * 冗余字段维护
    * */
    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        baseMapper.updateById(brand);
        if (StringUtils.hasLength(brand.getName())){
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }
    }

}

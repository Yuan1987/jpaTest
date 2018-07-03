package com.jpatest.modules.repository;
/*package com.jpatest.modules.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jpatest.querydsl.QueryDSLUtils;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import io.netty.util.internal.StringUtil;

public class RepositoryImpl extends RepositoryPersistenceQueryDSLImpl implements Repository {


    public RepositoryImpl(Provider provider) {
        super(provider);
    }

    
    @Override
    public LqxxVo getById(@ShardKey String zoneCode, String lqid) {

        Expression[] selector = QueryDSLUtils.select(QLqtjxx.lqtjxx, Lqtjxx.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));
        list.add(QXzqhXzdy.xzqhXzdy.mc.as("xzqhmc"));
        list.add(QLq.lq.lqdm.as("lqdm"));
        list.add(Expressions.as(JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                .where(QSjzd.sjzd.bm.eq(QLqtjxx.lqtjxx.lqlx).and(QSjzd.sjzd.fzm.eq(LQLX_FZM))), "lqlxmc"));
        selector = list.toArray(new Expression[0]);

        LqxxVo obj = getJPAQueryFactory()
                .select(Projections.bean(LqxxVo.class, selector))
                .from(QLqtjxx.lqtjxx)
                .join(QXzqhXzdy.xzqhXzdy).on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id))
                .join(QLq.lq).on(QLq.lq.id.eq(QLqtjxx.lqtjxx.lqid))
                .where(QLq.lq.id.eq(lqid).and(QLqtjxx.lqtjxx.zt.eq(0)).and(QLq.lq.zt.eq(0)))
                .fetchFirst();

        return obj;
    }

    @Override
    public Page<LqxxVo> getLqxxList(@ShardKey String zoneCode, LqxxQuery lq) {

        Expression[] selector = QueryDSLUtils.select(QLqtjxx.lqtjxx, Lqtjxx.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));
        list.add(QXzqhXzdy.xzqhXzdy.mc.as("xzqhmc"));
        list.add(Expressions.as(JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                .where(QSjzd.sjzd.bm.eq(QLqtjxx.lqtjxx.lqlx).and(QSjzd.sjzd.fzm.eq(LQLX_FZM))), "lqlxmc"));
        list.add(QLq.lq.lqdm.as("lqdm"));
        selector = list.toArray(new Expression[0]);

        BooleanExpression o = QXzqhXzdy.xzqhXzdy.bm.like(zoneCode + "%").and(QLqtjxx.lqtjxx.zt.eq(0)).and(QLq.lq.zt.eq(0)).and(QLq.lq.id.eq(QLqtjxx.lqtjxx.lqid));

        if (!StringUtil.isNullOrEmpty(lq.getLqlx())) {
            o = o.and(QLqtjxx.lqtjxx.lqlx.eq(lq.getLqlx()));
        }

        JPAQuery<LqxxVo> jpaQuery = getJPAQueryFactory().from(QLqtjxx.lqtjxx, QLq.lq)
                .select(Projections.bean(LqxxVo.class, selector)).
                        join(QXzqhXzdy.xzqhXzdy)
                .on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id))
                .where(o)
                .offset((lq.getPage() - 1) * lq.getSize()).limit(lq.getSize())
                .orderBy(QLq.lq.lqdm.asc());
        return new Page<>(jpaQuery.fetch(), jpaQuery.fetchCount(), lq.getSize(), lq.getPage());
    }

    @Override
    public Page<LqxxMiniVo> getLqxxMiniList(@ShardKey String zoneCode, LqxxQuery lq) {

        Expression<String> lqlxmc = Expressions.as(JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                .where(QSjzd.sjzd.bm.eq(QLqtjxx.lqtjxx.lqlx).and(QSjzd.sjzd.fzm.eq(LQLX_FZM))), "lqlxmc");

        Expression[] selector = {QLq.lq.id.as("lqid"), QXzqhXzdy.xzqhXzdy.mc.as("xzqhmc"), lqlxmc};

        JPAQuery<LqxxMiniVo> jpaQuery = getJPAQueryFactory().from(QLqtjxx.lqtjxx, QLq.lq)
                .select(Projections.bean(LqxxMiniVo.class, selector))
                .join(QXzqhXzdy.xzqhXzdy)
                .on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id))
                .where(QXzqhXzdy.xzqhXzdy.bm.like(zoneCode + "%").and(QLqtjxx.lqtjxx.zt.eq(0)).and(QLq.lq.zt.eq(0)).and(QLq.lq.id.eq(QLqtjxx.lqtjxx.lqid)))
                .offset((lq.getPage() - 1) * lq.getSize()).limit(lq.getSize())
                .orderBy(QLq.lq.lqdm.asc());
        return new Page<>(jpaQuery.fetch(), jpaQuery.fetchCount(), lq.getSize(), lq.getPage());
    }

    @Override
    public Page<LqpkzrVo> getLqkpzrList(@ShardKey String zoneCode, LqpkzrQuery lq) {

        Expression[] selector = QueryDSLUtils.select(QLqpkzr.lqpkzr, Lqpkzr.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));
        Expression<String> gbzntjsqkMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.gbzntjsqk).and(QSjzd.sjzd.fzm.eq(GBZNTJSQK_FZM))),
                "gbzntjsqkMc");
        list.add(gbzntjsqkMc);
        Expression<String> zrztlxMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.zrztlx).and(QSjzd.sjzd.fzm.eq(ZRZTLX_FZM))),
                "zrztlxMc");
        list.add(zrztlxMc);
        Expression<String> zrdbzjlxMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.zrdbzjlx).and(QSjzd.sjzd.fzm.eq(ZRDBZJLX_FZM))),
                "zrdbzjlxMc");
        list.add(zrdbzjlxMc);

        list.add(QLqpk.lqpk.lqpkmc.as("pkmc"));
        list.add(QLqpk.lqpk.lqpkdm.as("lqpkdm"));

        selector = list.toArray(new Expression[0]);

        BooleanExpression o = QXzqhXzdy.xzqhXzdy.bm.like(zoneCode + "%").and(QLqpkzr.lqpkzr.zt.eq(0)).and(QLqpk.lqpk.zt.eq(0)).and(QLqpkzr.lqpkzr.lqpkid.eq(QLqpk.lqpk.id));

        if (!StringUtil.isNullOrEmpty(lq.getLqpkmc())) {
            o = o.and(QLqpk.lqpk.lqpkmc.like(lq.getLqpkmc() + "%").or(QLqpkzr.lqpkzr.zrztmc.like(lq.getLqpkmc() + "%")));
        }

        String mcOrZjhm = lq.getZrztdbmcOrZjhm();

        if (!StringUtil.isNullOrEmpty(mcOrZjhm)) {
            o = o.and(QLqpkzr.lqpkzr.zrztdbmc.startsWith(mcOrZjhm).or(QLqpkzr.lqpkzr.zrdbzjhm.startsWith(mcOrZjhm)));
        }

        JPAQuery<LqpkzrVo> jpaQuery = getJPAQueryFactory().from(QLqpkzr.lqpkzr, QLqpk.lqpk)
                .select(Projections.bean(LqpkzrVo.class, selector))
                .join(QXzqhXzdy.xzqhXzdy)
                .on(QLqpkzr.lqpkzr.szdy.eq(QXzqhXzdy.xzqhXzdy.id))
                .where(o)
                .offset((lq.getPage() - 1) * lq.getSize()).limit(lq.getSize());

        return new Page<LqpkzrVo>(jpaQuery.fetch(), jpaQuery.fetchCount(), lq.getSize(), lq.getPage());
    }


    @Override
    public int updateLqkpzr(@ShardKey String zoneCode, LqpkzrDTO obj) {
        return update(QLqpkzr.lqpkzr, obj);
    }
    
    @Override
    public int addLqwdzl(@ShardKey String zoneCode, Lqwdzlgx gx, Lqwdzl wdzl) {
        add(gx);
        return add(wdzl);
    }

    @Override
    public Page<LqwdzlVo> getLqwdzlList(@ShardKey String zoneCode, String ywId, BaseQuery query) {


        Expression[] selector = QueryDSLUtils.select(QLqwdzl.lqwdzl, Lqwdzl.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));

        Expression<String> wdlxmc = Expressions.as(JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                .where(QSjzd.sjzd.bm.eq(QLqwdzl.lqwdzl.wdlx).and(QSjzd.sjzd.fzm.eq(LQWDZLLX_FZM))), "wdlxmc");
        list.add(wdlxmc);

        selector = list.toArray(new Expression[0]);

        JPAQuery<LqwdzlVo> jpaQuery = getJPAQueryFactory().from(QLqwdzl.lqwdzl, QLqwdzlgx.lqwdzlgx)
                .select(Projections.bean(LqwdzlVo.class, selector))
                .where(QLqwdzl.lqwdzl.zt.eq(0).and(QLqwdzlgx.lqwdzlgx.zt.eq(0)).and(QLqwdzl.lqwdzl.id.eq(QLqwdzlgx.lqwdzlgx.wdid)).and(QLqwdzlgx.lqwdzlgx.ywid.eq(ywId)))
                .offset((query.getPage() - 1) * query.getSize()).limit(query.getSize())
                .orderBy(QLqwdzl.lqwdzl.wdmc.asc());

        return new Page<LqwdzlVo>(jpaQuery.fetch(), jpaQuery.fetchCount(), query.getSize(), query.getPage());
    }

    @Override
    public Page<LqwdzlVo> getWdList(@ShardKey String zoneCode, String ywId, BaseQuery query) {


        Expression[] selector = QueryDSLUtils.select(QLqwdzl.lqwdzl, Lqwdzl.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));

        Expression<String> wdlxmc = Expressions.as(JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                .where(QSjzd.sjzd.bm.eq(QLqwdzl.lqwdzl.wdlx).and(QSjzd.sjzd.fzm.eq(LQWDZLLX_FZM))), "wdlxmc");
        list.add(wdlxmc);

        selector = list.toArray(new Expression[0]);

        JPAQuery<LqwdzlVo> jpaQuery = getJPAQueryFactory().from(QLqwdzl.lqwdzl, QLqwdzlgx.lqwdzlgx)
                .select(Projections.bean(LqwdzlVo.class, selector))
                .where(QLqwdzl.lqwdzl.zt.eq(0).and(QLqwdzlgx.lqwdzlgx.zt.eq(0)).and(QLqwdzl.lqwdzl.id.eq(QLqwdzlgx.lqwdzlgx.wdid)).and(QLqwdzlgx.lqwdzlgx.ywid.eq(ywId)))
                .offset((query.getPage() - 1) * query.getSize()).limit(query.getSize())
                .orderBy(QLqwdzl.lqwdzl.wdmc.asc());
        //处理路径
        List<LqwdzlVo> results = jpaQuery.fetch();
        for(int i = 0 ; i < results.size() ; i++ ){
            results.get(i).setUrl("/api/tworegion/lq/wdzl/{"+zoneCode+"}/"+results.get(i).getId()+"/inline");
        }

        return new Page<LqwdzlVo>(results, jpaQuery.fetchCount(), query.getSize(), query.getPage());
    }

    @Override
    public long updateLqwdzl(@ShardKey String zoneCode, String id, String dwmc) {
        return getJPAQueryFactory().update(QLqwdzl.lqwdzl).set(QLqwdzl.lqwdzl.wdmc, dwmc).where(QLqwdzl.lqwdzl.id.eq(id)).execute();
    }

    @Override
    public int deleteBtffxx(BtffDTO btff) {
        return delete(QSqxx.sqxx, btff);
    }

    @Override
    public void bringBtffxx(String lqzrztid, String type) {
        getJPAQueryFactory().update(QSqxx.sqxx).set(QSqxx.sqxx.zt, getStatus(type)).where(QSqxx.sqxx.zrtzid.eq(lqzrztid)).execute();
    }

    @Override
    public LqpkzrSqxxDTO findById(String id) {
        Expression[] selector = QueryDSLUtils.select(QLqpkzr.lqpkzr, Lqpkzr.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));
        list.add(QLqpk.lqpk.lqpkdm.as("lqpkdm"));
        list.add(QSqxx.sqxx.bgyy.as("bgyy"));
        list.add(QLqbzp.lqbzp.bzpbh.as("bzpbh"));
        selector = list.toArray(new Expression[0]);
        LqpkzrSqxxDTO o = getJPAQueryFactory()
                .from(QSqxx.sqxx)
                .leftJoin(QLqpkzr.lqpkzr).on(QSqxx.sqxx.lqpkid.eq(QLqpkzr.lqpkzr.lqpkid))
                .leftJoin(QLqpk.lqpk).on(QLqpkzr.lqpkzr.lqpkid.eq(QLqpk.lqpk.id))
                .leftJoin(QLqbzp.lqbzp).on(QLqbzp.lqbzp.id.eq(QLqpkzr.lqpkzr.bzpid))
                .select(Projections.bean(LqpkzrSqxxDTO.class, selector))
                .where(QSqxx.sqxx.bz.eq(id).and(QSqxx.sqxx.lwlx.eq(1)))
                .fetchFirst();
        return o;
    }


    @Override
    public Page<LqywbtffVo> getSqxxListByIdJb(String dybm, Integer type, BaseQuery query) {
        JPAQuery<LqywbtffVo> jpaQuery =getJPAQueryFactory()
                .from(QSqxx.sqxx).distinct()
                        .select(Projections.bean(LqywbtffVo.class,QSqxx.sqxx.bz.as("id"),QSqxx.sqxx.zt,QSqxx.sqxx.lwlx
                        ,QSqxx.sqxx.slsj,QSqxx.sqxx.sljssj,QSqxx.sqxx.slry))
                        .where(QSqxx.sqxx.lwlx.eq(type)
                        .and(QSqxx.sqxx.zt.eq(4)))
                        .offset((query.getPage() - 1) * query.getSize()).limit(query.getSize())
                        .orderBy(QSqxx.sqxx.slsj.desc());
        Page<LqywbtffVo> lqywbtffVoPage=new Page<LqywbtffVo>(jpaQuery.fetch(), jpaQuery.fetchCount(), query.getSize(), query.getPage());
        return getZtStr(lqywbtffVoPage);
    }

    
    @Override
    public Page<LqywbtffVo> getSqxxListByIdDb(String dybm, Integer type, BaseQuery query) {
        JPAQuery<LqywbtffVo> jpaQuery =getJPAQueryFactory()
                .from(QSqxx.sqxx).distinct()
                        .select(Projections.bean(LqywbtffVo.class,QSqxx.sqxx.bz.as("id"),QSqxx.sqxx.zt,QSqxx.sqxx.lwlx
                        ,QSqxx.sqxx.slsj,QSqxx.sqxx.sljssj,QSqxx.sqxx.slry))
                        .where(QSqxx.sqxx.lwlx.eq(type)
                        .and(QSqxx.sqxx.zt.notIn(4,0)))
                        .offset((query.getPage() - 1) * query.getSize()).limit(query.getSize())
                        .orderBy(QSqxx.sqxx.slsj.desc());
        Page<LqywbtffVo> lqywbtffVoPage=new Page<LqywbtffVo>(jpaQuery.fetch(), jpaQuery.fetchCount(), query.getSize(), query.getPage());
        return getZtStr(lqywbtffVoPage);
    }
    

    @Override
    public Page<LqpkzrWithBtffVo> getZrztList(String zoneCode,String key, BaseQuery query) {
        
        Expression[] selector = QueryDSLUtils.select(QLqpkzr.lqpkzr, Lqpkzr.class);
        List<Expression> list = new ArrayList<Expression>();
        list.addAll(Arrays.asList(selector));
        Expression<String> gbzntjsqkMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.gbzntjsqk).and(QSjzd.sjzd.fzm.eq(GBZNTJSQK_FZM))),
                "gbzntjsqkMc");
        list.add(gbzntjsqkMc);
        Expression<String> zrztlxMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.zrztlx).and(QSjzd.sjzd.fzm.eq(ZRZTLX_FZM))),
                "zrztlxMc");
        list.add(zrztlxMc);
        Expression<String> zrdbzjlxMc = Expressions.as(
                JPAExpressions.select(QSjzd.sjzd.mc).from(QSjzd.sjzd)
                        .where(QSjzd.sjzd.bm.eq(QLqpkzr.lqpkzr.zrdbzjlx).and(QSjzd.sjzd.fzm.eq(ZRDBZJLX_FZM))),
                "zrdbzjlxMc");
        list.add(zrdbzjlxMc);

        list.add(QLqpk.lqpk.lqpkmc.as("pkmc"));
        list.add(QLqpk.lqpk.lqpkdm.as("lqpkdm"));
        list.add(QSqxx.sqxx.btje.as("btje"));

        selector = list.toArray(new Expression[0]);

        BooleanExpression o = QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode).and(QLqpkzr.lqpkzr.zt.eq(0)).and(QLqpk.lqpk.zt.eq(0)).and(QLqpkzr.lqpkzr.lqpkid.eq(QLqpk.lqpk.id));

        if (!StringUtil.isNullOrEmpty(key)) {
            o = o.and(QLqpk.lqpk.lqpkmc.startsWith(key).or(QLqpkzr.lqpkzr.zrztmc.startsWith(key)));
        }

        JPAQuery<LqpkzrWithBtffVo> jpaQuery = getJPAQueryFactory().from(QLqpkzr.lqpkzr, QLqpk.lqpk)
                .select(Projections.bean(LqpkzrWithBtffVo.class, selector))
                .join(QXzqhXzdy.xzqhXzdy)
                .on(QLqpkzr.lqpkzr.szdy.eq(QXzqhXzdy.xzqhXzdy.id))
                .leftJoin(QSqxx.sqxx).on(QLqpkzr.lqpkzr.id.eq(QSqxx.sqxx.zrtzid).and(QSqxx.sqxx.zt.eq(0).and(QSqxx.sqxx.lwlx.eq(2))))
                .where(o)
                .offset((query.getPage() - 1) * query.getSize()).limit(query.getSize());

        return new Page<LqpkzrWithBtffVo>(jpaQuery.fetch(), jpaQuery.fetchCount(), query.getSize(), query.getPage());
        
    }
    
    
    @Override
    public int LqpkzrBtffImport(List<BtffDTO> list) {
        
        for(BtffDTO btff: list){
            
           String id= getJPAQueryFactory().from(QLqpkzr.lqpkzr, QSqxx.sqxx, QLqpk.lqpk)
            .select(QSqxx.sqxx.id)
            .where(QLqpkzr.lqpkzr.lqpkid.eq(QLqpk.lqpk.id).
                    and(QLqpkzr.lqpkzr.id.eq(QSqxx.sqxx.zrtzid))
                    .and(QSqxx.sqxx.lwlx.eq(YWLX.BTFF.getValue()))
                    .and(QSqxx.sqxx.zt.eq(0))
                    .and(QSqxx.sqxx.zrtzid.eq(btff.getZrtzid())))
                    .fetchFirst();
            
            if(StringUtils.isBlank(id)) {
                add(QSqxx.sqxx, btff);
            }else {
                getJPAQueryFactory().update(QSqxx.sqxx).set(QSqxx.sqxx.btje, btff.getBtje()).where(QSqxx.sqxx.id.eq(id)).execute();
            }
        }
        return 0;
    }
    
}
*/
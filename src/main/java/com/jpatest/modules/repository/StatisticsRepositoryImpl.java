/*package com.jpatest.modules.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.hibernate.RepositoryPersistenceQueryDSLImpl;
import com.yulintu.thematic.data.sharding.ShardConfig;
import com.yulintu.tworegion.entities.enums.ZT;
import com.yulintu.tworegion.entities.querydsl.QJbntbhtb;
import com.yulintu.tworegion.entities.querydsl.QLqdk;
import com.yulintu.tworegion.entities.querydsl.QLqpk;
import com.yulintu.tworegion.entities.querydsl.QLqpkzr;
import com.yulintu.tworegion.entities.querydsl.QLqtjxx;
import com.yulintu.tworegion.entities.querydsl.QSjzd;
import com.yulintu.tworegion.entities.querydsl.QTjgcdzdw;
import com.yulintu.tworegion.entities.querydsl.QXzqhXzdy;

*//**
 * @author YuanHB
 *
 *//*
public class StatisticsRepositoryImpl extends RepositoryPersistenceQueryDSLImpl implements StatisticsRepository {
    
    private static final String LQLX_FZM = "62015";
    
    private static final String ZRXTLX_FZM = "62011";
    
    private static final String PDJB_FZM = "62014";
    
    private static final String TJGC_FXM = "62013";
    
    private static final String ZLDJ_FXM = "61005";
    

    public StatisticsRepositoryImpl(Provider provider) {
        super(provider);
    }

    @Override   
    public Double lqmjzh(String zoneCode) {

        Double d = getJPAQueryFactory().select(QLqtjxx.lqtjxx.lqmjm.sum()).from(QLqtjxx.lqtjxx)
                .innerJoin(QXzqhXzdy.xzqhXzdy)
                .on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqtjxx.lqtjxx.zt.eq(0)))
                .where(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).fetchFirst();

        return d == null ? 0 : d;
    }
    
    @Override
    public List<Map<String, Object>> lqmjByLqlx(String zoneCode) {
        
       List<Tuple> l= getJPAQueryFactory()
               .select(QLqtjxx.lqtjxx.lqmjm.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
               .from(QLqtjxx.lqtjxx)
               .join(QXzqhXzdy.xzqhXzdy)
               .on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)))
               .rightJoin(QSjzd.sjzd)
               .on(QLqtjxx.lqtjxx.lqlx.eq(QSjzd.sjzd.bm).and(QLqtjxx.lqtjxx.zt.eq(ZT.Normal.getZt())))
               
               .where((QSjzd.sjzd.fzm.eq(LQLX_FZM)).and(QSjzd.sjzd.bm.notIn("10","20")))
               .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
               .orderBy(QSjzd.sjzd.bm.asc()).fetch();
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(QSjzd.sjzd.mc));
            map.put("mj", tuple.get(QLqtjxx.lqtjxx.lqmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> lqmjByChildXzqh(String zoneCode) {
       
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqtjxx.lqtjxx.lqmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqtjxx.lqtjxx).on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqtjxx.lqtjxx.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Double d =getJPAQueryFactory().select(QLqtjxx.lqtjxx.lqmjm.sum()).from(QLqtjxx.lqtjxx).where(QLqtjxx.lqtjxx.szdy.eq(szdy).and(QLqtjxx.lqtjxx.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("mj", d == null ? 0 : d);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("mj", tuple.get(QLqtjxx.lqtjxx.lqmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> lqslByChildXzqh(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqtjxx.lqtjxx.lqid.count()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqtjxx.lqtjxx).on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqtjxx.lqtjxx.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            long count = getJPAQueryFactory().select(QLqtjxx.lqtjxx.id).from(QLqtjxx.lqtjxx).where(QLqtjxx.lqtjxx.szdy.eq(szdy).and(QLqtjxx.lqtjxx.zt.eq(0))).fetchCount();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", count);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqtjxx.lqtjxx.lqid.count()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> lqhz(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqtjxx.lqtjxx.lqid.count(),QLqtjxx.lqtjxx.lqmj.sum(),QLqtjxx.lqtjxx.lqmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqtjxx.lqtjxx).on(QLqtjxx.lqtjxx.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqtjxx.lqtjxx.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Tuple tuple = getJPAQueryFactory().select(QLqtjxx.lqtjxx.id.count(),QLqtjxx.lqtjxx.lqmj.sum(),QLqtjxx.lqtjxx.lqmjm.sum())
                    .from(QLqtjxx.lqtjxx).where(QLqtjxx.lqtjxx.szdy.eq(szdy).and(QLqtjxx.lqtjxx.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", tuple.get(QLqtjxx.lqtjxx.id.count()));
            map.put("mj", tuple.get(QLqtjxx.lqtjxx.lqmj.sum()));
            map.put("mjm", tuple.get(QLqtjxx.lqtjxx.lqmjm.sum()));
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqtjxx.lqtjxx.lqid.count()));
            map.put("mj", tuple.get(QLqtjxx.lqtjxx.lqmj.sum()));
            map.put("mjm", tuple.get(QLqtjxx.lqtjxx.lqmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public Double pkmjzh(String zoneCode) {
        
        Double d = getJPAQueryFactory().select(QLqpk.lqpk.lqpkmjm.sum()).from(QLqpk.lqpk)
                .innerJoin(QXzqhXzdy.xzqhXzdy)
                .on(QLqpk.lqpk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqpk.lqpk.zt.eq(0)))
                .where(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).fetchFirst();

        return d == null ? 0 : d;
    }
    
    @Override
    public List<Map<String, Object>> pkmjByChildXzqh(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqpk.lqpk.lqpkmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqpk.lqpk).on(QLqpk.lqpk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqpk.lqpk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Double d =getJPAQueryFactory().select(QLqpk.lqpk.lqpkmjm.sum()).from(QLqpk.lqpk).where(QLqpk.lqpk.szdy.eq(szdy).and(QLqpk.lqpk.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("mj", d == null ? 0 : d);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("mj", tuple.get(QLqpk.lqpk.lqpkmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> pkslByChildXzqh(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqpk.lqpk.id.count()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqpk.lqpk).on(QLqpk.lqpk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqpk.lqpk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            long count = getJPAQueryFactory().select(QLqpk.lqpk.id).from(QLqpk.lqpk).where(QLqpk.lqpk.szdy.eq(szdy).and(QLqpk.lqpk.zt.eq(0))).fetchCount();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", count);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqpk.lqpk.id.count()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> pkhz(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqpk.lqpk.id.count(),QLqpk.lqpk.lqpkmj.sum(),QLqpk.lqpk.lqpkmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqpk.lqpk).on(QLqpk.lqpk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqpk.lqpk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Tuple tuple = getJPAQueryFactory().select(QLqpk.lqpk.id.count(),QLqpk.lqpk.lqpkmj.sum(),QLqpk.lqpk.lqpkmjm.sum())
                    .from(QLqpk.lqpk).where(QLqpk.lqpk.szdy.eq(szdy).and(QLqpk.lqpk.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", tuple.get(QLqpk.lqpk.id.count()));
            map.put("mj", tuple.get(QLqpk.lqpk.lqpkmj.sum()));
            map.put("mjm", tuple.get(QLqpk.lqpk.lqpkmjm.sum()));
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqpk.lqpk.id.count()));
            map.put("mj", tuple.get(QLqpk.lqpk.lqpkmj.sum()));
            map.put("mjm", tuple.get(QLqpk.lqpk.lqpkmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> pkmjByLqlx(String zoneCode) {
        
        List<Tuple> l2= getJPAQueryFactory()
                .select(QLqpk.lqpk.lqpkmjm.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QLqpk.lqpk)
                .join(QXzqhXzdy.xzqhXzdy).on(QLqpk.lqpk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).and(QLqpk.lqpk.zt.eq(ZT.Normal.getZt())))
                .join(QLqtjxx.lqtjxx).on(QLqpk.lqpk.lqid.eq(QLqtjxx.lqtjxx.lqid).and(QLqtjxx.lqtjxx.zt.eq(ZT.Normal.getZt())))
                .rightJoin(QSjzd.sjzd).on(QLqtjxx.lqtjxx.lqlx.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(LQLX_FZM)).and(QSjzd.sjzd.bm.notIn("10","20")))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l2.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("mj", tuple.get(QLqpk.lqpk.lqpkmjm.sum()));
                    return map;
                }).collect(Collectors.toList());
    }

    @Override
    public Double dkmjzh(String zoneCode) {
        
        Double d = getJPAQueryFactory().select(QLqdk.lqdk.lqdkmjm.sum()).from(QLqdk.lqdk)
                .innerJoin(QXzqhXzdy.xzqhXzdy)
                .on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqdk.lqdk.zt.eq(0)))
                .where(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).fetchFirst();

        return d == null ? 0 : d;
    }

    @Override
    public List<Map<String, Object>> dkmjByChildXzqh(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqdk.lqdk.lqdkmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqdk.lqdk).on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqdk.lqdk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Double d =getJPAQueryFactory().select(QLqdk.lqdk.lqdkmjm.sum()).from(QLqdk.lqdk).where(QLqdk.lqdk.szdy.eq(szdy).and(QLqdk.lqdk.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("mj", d == null ? 0 : d);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("mj", tuple.get(QLqdk.lqdk.lqdkmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> dkslByChildXzqh(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqdk.lqdk.id.count()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqdk.lqdk).on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqdk.lqdk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            long count = getJPAQueryFactory().select(QLqdk.lqdk.id).from(QLqdk.lqdk).where(QLqdk.lqdk.szdy.eq(szdy).and(QLqdk.lqdk.zt.eq(0))).fetchCount();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", count);
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqdk.lqdk.id.count()));
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> dkhz(String zoneCode) {
        
        Tuple dzdyTuple= getJPAQueryFactory().select(QXzqhXzdy.xzqhXzdy.id,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.bm.eq(zoneCode)).fetchFirst();
        
        if(dzdyTuple == null || StringUtils.isBlank(dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id))) {
            return null;
        }
        
        String szdy = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.id);
        
        String szdyMc = dzdyTuple.get(QXzqhXzdy.xzqhXzdy.mc);
        
        SQLQuery<Tuple> xjdy = SQLExpressions.select(QXzqhXzdy.xzqhXzdy.bm,QXzqhXzdy.xzqhXzdy.mc).from(QXzqhXzdy.xzqhXzdy).where(QXzqhXzdy.xzqhXzdy.sjid.eq(szdy));
        
        PathBuilder<Tuple> xjdyAlias = new PathBuilder<>(Tuple.class, "xjdy");
        
        List<Tuple> l =  getSQLQueryFactory(null).select(xjdyAlias.get("mc"),xjdyAlias.get("bm"),QLqdk.lqdk.id.count(),QLqdk.lqdk.lqdkmj.sum(),QLqdk.lqdk.lqdkmjm.sum()).from(xjdy,xjdyAlias)
        .leftJoin(QXzqhXzdy.xzqhXzdy).on(QXzqhXzdy.xzqhXzdy.bm.startsWith(xjdyAlias.get("bm",String.class)))
        .leftJoin(QLqdk.lqdk).on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QLqdk.lqdk.zt.eq(0)))
        .groupBy(xjdyAlias.get("mc")).groupBy(xjdyAlias.get("bm"))
        .orderBy()
        .fetch();
        
        if(l == null || l.isEmpty()) {
            Tuple tuple = getJPAQueryFactory().select(QLqdk.lqdk.id.count(),QLqdk.lqdk.lqdkmj.sum(),QLqdk.lqdk.lqdkmjm.sum())
                    .from(QLqdk.lqdk).where(QLqdk.lqdk.szdy.eq(szdy).and(QLqdk.lqdk.zt.eq(0))).fetchFirst();
            
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", szdyMc);
            map.put("sl", tuple.get(QLqdk.lqdk.id.count()));
            map.put("mj", tuple.get(QLqdk.lqdk.lqdkmj.sum()));
            map.put("mjm", tuple.get(QLqdk.lqdk.lqdkmjm.sum()));
            
            List<Map<String, Object>> list =new ArrayList<>();
            
            list.add(map);
            
            return list;
        }
        
        return l.stream().map(tuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("mc", tuple.get(xjdyAlias.get("mc")));
            map.put("sl", tuple.get(QLqdk.lqdk.id.count()));
            map.put("mj", tuple.get(QLqdk.lqdk.lqdkmj.sum()));
            map.put("mjm", tuple.get(QLqdk.lqdk.lqdkmjm.sum()));
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> dkmjByLqlx(String zoneCode) {
        
        List<Tuple> l= getJPAQueryFactory()
                .select(QLqdk.lqdk.lqdkmjm.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QLqdk.lqdk)
                .join(QXzqhXzdy.xzqhXzdy).on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)))
                .join(QLqpk.lqpk).on(QLqdk.lqdk.lqpkid.eq(QLqpk.lqpk.id).and(QLqdk.lqdk.zt.eq(ZT.Normal.getZt())))
                .join(QLqtjxx.lqtjxx).on(QLqpk.lqpk.lqid.eq(QLqtjxx.lqtjxx.lqid).and(QLqpk.lqpk.zt.eq(ZT.Normal.getZt())))
                .rightJoin(QSjzd.sjzd).on(QLqtjxx.lqtjxx.lqlx.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(LQLX_FZM)).and(QSjzd.sjzd.bm.notIn("10","20")))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("mj", tuple.get(QLqdk.lqdk.lqdkmjm.sum()));
                    return map;
                }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> pdjb(String zoneCode) {
        
        List<Tuple> l= getJPAQueryFactory()
                .select(QJbntbhtb.jbntbhtb.jbntmj.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QJbntbhtb.jbntbhtb)
                .join(QXzqhXzdy.xzqhXzdy).on(QJbntbhtb.jbntbhtb.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)))
                .rightJoin(QSjzd.sjzd).on(QJbntbhtb.jbntbhtb.pdjb.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(PDJB_FZM)))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("mj", tuple.get(QJbntbhtb.jbntbhtb.jbntmj.sum()));
                    return map;
                }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> zldj(String zoneCode) {
        
        List<Tuple> l= getJPAQueryFactory()
                .select(QLqdk.lqdk.lqdkmjm.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QLqdk.lqdk)
                .join(QXzqhXzdy.xzqhXzdy).on(QLqdk.lqdk.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)))
                .rightJoin(QSjzd.sjzd).on(QLqdk.lqdk.zldjdm.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(ZLDJ_FXM)))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("mj", tuple.get(QLqdk.lqdk.lqdkmjm.sum()));
                    return map;
                }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> ghzrztBylx(String zoneCode) {
        
        List<Tuple> l= getJPAQueryFactory()
                .select(QLqpkzr.lqpkzr.id.count(),QLqpk.lqpk.lqpkmjm.sum(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QLqpkzr.lqpkzr)
                .join(QXzqhXzdy.xzqhXzdy).on(QLqpkzr.lqpkzr.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).and(QLqpkzr.lqpkzr.zt.eq(ZT.Normal.getZt())))
                .join(QLqpk.lqpk).on(QLqpkzr.lqpkzr.lqpkid.eq(QLqpk.lqpk.id).and(QLqpk.lqpk.zt.eq(ZT.Normal.getZt())))
                .rightJoin(QSjzd.sjzd).on(QLqpkzr.lqpkzr.zrztlx.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(ZRXTLX_FZM)))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("sl", tuple.get(QLqpkzr.lqpkzr.id.count()));
                    map.put("mj", tuple.get(QLqpk.lqpk.lqpkmjm.sum()));
                    return map;
                }).collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> tjgcsl(String zoneCode) {
        
        List<Tuple> l= getJPAQueryFactory()
                .select(QTjgcdzdw.tjgcdzdw.id.count(),QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .from(QTjgcdzdw.tjgcdzdw)
                .join(QXzqhXzdy.xzqhXzdy).on(QTjgcdzdw.tjgcdzdw.szdy.eq(QXzqhXzdy.xzqhXzdy.id).and(QXzqhXzdy.xzqhXzdy.bm.startsWith(zoneCode)).and(QTjgcdzdw.tjgcdzdw.zt.eq(ZT.Normal.getZt())))
                .rightJoin(QSjzd.sjzd).on(QTjgcdzdw.tjgcdzdw.tjgclx.eq(QSjzd.sjzd.bm))
                
                .where((QSjzd.sjzd.fzm.eq(TJGC_FXM)))
                .groupBy(QSjzd.sjzd.mc,QSjzd.sjzd.bm)
                .orderBy(QSjzd.sjzd.bm.asc()).fetch();
                
                return l.stream().map(tuple -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("mc", tuple.get(QSjzd.sjzd.mc));
                    map.put("sl", tuple.get(QTjgcdzdw.tjgcdzdw.id.count()));
                    return map;
                }).collect(Collectors.toList());
    }
}
*/
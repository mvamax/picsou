package io.picsou.service;

import io.picsou.domain.stat.Histogram3Fields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class StatService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<String, Map<String, Long>> getHistogram(String year){
		Map<String,Map<String,Long>> map = new HashMap<>();
		System.out.println("start histogram");
		List<Histogram3Fields> stats = this.jdbcTemplate.query(
				"select to_char(c.dateexecutioncontrat, 'MM') as month,pc.typeproduit as categorie,SUM(pc.prix) as sum from t_contrat c,t_produitcontrat pc "+
						" where c.id=pc.contrat_id and c.dateexecutioncontrat is not null "
						+ "and c.etatcontrat_id=30 "
						+ "and to_char(c.dateexecutioncontrat, 'YYYY')='"+year+
						"' group by to_char(dateexecutioncontrat, 'MM'),pc.typeproduit",
		        new RowMapper<Histogram3Fields>() {
		            public Histogram3Fields mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	Histogram3Fields stat = new Histogram3Fields();
		            	stat.setC1(rs.getString("month"));
		            	stat.setC2(rs.getString("categorie"));
		                stat.setStat(rs.getLong("sum"));
		                return stat;
		            }
		        });
		//Pour chaque category Mois,Long
		for(Histogram3Fields stat :stats){
			
			if(map.get(stat.getC2())==null){
				Map<String, Long> a = new HashMap<>();
				a.put(stat.getC1(), stat.getStat());
				map.put(stat.getC2(), a);
			}else{
				 Map<String, Long> a = map.get(stat.getC2());
				 a.put(stat.getC1(), stat.getStat());
				 map.put(stat.getC2(),a);
			}

			
		}
		return map;
	}
}

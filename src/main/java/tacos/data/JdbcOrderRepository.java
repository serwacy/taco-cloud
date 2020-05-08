package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
   private SimpleJdbcInsert orderInserter;
   private SimpleJdbcInsert orderTacoInserter;
   private ObjectMapper objectMapper;

   @Autowired
   public JdbcOrderRepository(final JdbcTemplate jdbcTemplate){
      this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("Taco_Order")
              .usingGeneratedKeyColumns("id");
      this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("Taco_Order_Tacos");
      this.objectMapper = new ObjectMapper();
   }

   @Override
   public Order save(final Order order) {
      order.setPlacedAt(new Date());
      long orderId = saveOrderDetails(order);
      order.setId(orderId);
      List<Taco> tacos = order.getTacos();
      for (Taco taco: tacos) {
         saveTacoOrder(taco, orderId);
      }
      return order;
   }

   private long saveOrderDetails(final Order order){
      @SuppressWarnings("unchecked")
      Map<String, Object> values = objectMapper.convertValue(order, Map.class);
      values.put("placedAt", order.getPlacedAt());
      return orderInserter
              .executeAndReturnKey(values)
              .longValue();
   }

   private void saveTacoOrder(final Taco taco, final long orderId){
      Map<String, Object> values = new HashMap<>();
      values.put("tacoOrder", orderId);
      values.put("taco", taco.getId());
      orderTacoInserter.execute(values);
   }
}

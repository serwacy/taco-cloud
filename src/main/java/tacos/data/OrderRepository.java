package tacos.data;

import tacos.Order;

public interface OrderRepository {
   Order save(final Order order);
}

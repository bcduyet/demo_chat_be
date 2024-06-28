package co.jp.xeex.chat.base;

import co.jp.xeex.chat.domains.taskmngr.task.dto.OrderByDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * PageInfo represents the PageRequest
 * (v_long thấy thịnh dùng nhiều nơi rườm rà quá)
 * 
 * @author v_long
 */
@AllArgsConstructor
public class PageInfo {
    private Integer pageIdx;
    private Integer perPage = 20;
    private List<OrderByDto> orderFields;

    public PageRequest getPageRequest() {
        Sort sort = Sort.unsorted();
        if (this.orderFields != null && !this.orderFields.isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (OrderByDto orderByDto : this.orderFields) {
                Order order = new Order(orderByDto.getOrderType(), orderByDto.getFieldName());
                orders.add(order);
            }
            sort = Sort.by(orders);
        }
        Integer page = (this.pageIdx == null) ? 1 : this.pageIdx;
        return PageRequest.of(page - 1, perPage, sort);
    }
}

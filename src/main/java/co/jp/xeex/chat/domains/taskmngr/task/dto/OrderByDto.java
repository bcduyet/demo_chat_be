package co.jp.xeex.chat.domains.taskmngr.task.dto;

import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

/**
 * OrderDto
 * 
 * @author q_thinh
 */
@Data
public class OrderByDto {
    private String fieldName;
    private Direction orderType = Direction.ASC;
}

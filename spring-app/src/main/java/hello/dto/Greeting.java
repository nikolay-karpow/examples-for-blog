package hello.dto;

import lombok.Data;

@Data
public class Greeting {
    private final Person from;
    private final String msg;
}

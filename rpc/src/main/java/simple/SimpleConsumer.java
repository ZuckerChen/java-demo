package simple;

import consumer.ConsumerBootstrap;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 11:41 AM
 * @Description
 */
public class SimpleConsumer {
    public static void main(String[] args) {
        ConsumerBootstrap consumerBootstrap = new ConsumerBootstrap();
        ITest iTest = consumerBootstrap.findService(ITest.class);
        TestDTO dto = new TestDTO();
        dto.setParam1("cz1");
        TestVO vo = iTest.test2(dto);
        System.out.println("vo:" + vo);

        String hahaha = iTest.test1("hahaha");
        System.out.println("hahaha:" + hahaha);
        iTest.test3();
    }
}

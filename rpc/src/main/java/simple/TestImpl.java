package simple;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 11:38 AM
 * @Description
 */
public class TestImpl implements ITest {
    @Override
    public String test1(String s) {
        System.out.println("test1");
        return "test1";
    }

    @Override
    public TestVO test2(TestDTO dto) {
        System.out.println("test2接收到 dto:" + dto);

        TestVO vo = new TestVO();
        vo.setName("test2");
        vo.setAge(13);
        return vo;
    }

    @Override
    public void test3() {
        System.out.println("test3");
    }
}

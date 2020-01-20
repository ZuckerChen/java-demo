package distributedtransaction.coordinator;

/**
 * @author: zucker
 * @description:
 * @date: 2019-11-07 11:09
 */
public interface Participant {
    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();
}

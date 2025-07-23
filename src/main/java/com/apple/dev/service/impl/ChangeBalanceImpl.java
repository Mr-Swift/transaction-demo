package com.apple.dev.service.impl;

import com.apple.dev.mapper.SysUserMapper;
import com.apple.dev.service.ChangeBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeBalanceImpl implements ChangeBalance {

    private final SysUserMapper sysUserMapper;

    private final TransactionTemplate transactionTemplate;

    private final PlatformTransactionManager platformTransactionManager;


    @Override
    public void change1() {

        log.debug("开启事务前打印...");

        Boolean result = null;
        try {
            result = transactionTemplate.execute(status -> {

                // 注册事务完成后的回调
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        // 事务成功提交后的操作放在这里
                        log.debug("事务已成功提交，执行后续操作...");
                    }

                    @Override
                    public void afterCompletion(int status) {
                        // 可以在这里处理无论事务成功与否都需要做的清理工作
                        log.debug("事务已结束，执行后续操作...");
                    }
                });

                this.addBalance();


                return Boolean.TRUE;

            });

            log.debug("事务代码块结束后打印1...");
        } catch (Exception e) {
            log.error("捕捉到异常:", e);
        } finally {
            log.debug("finally执行...");
        }
        log.debug("事务代码块结束后打印2...");

        log.debug("result={}", result);

    }

    @Override
    public void change2() {
        log.debug("开启事务前...");

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(def);

        // 注册事务提交后释放锁
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    log.debug("事务提交后执行...");
                }

                @Override
                public void afterCompletion(int status) {
                    log.debug("事务结束后执行...");
                }
            });
        }


        try {
            this.addBalance();

            platformTransactionManager.commit(status); // 提交事务
        } catch (Exception e) {
            platformTransactionManager.rollback(status); // 回滚事务
            log.debug("事务抛出异常后执行...");
            log.error("事务抛出异常:", e);
            throw e;
        } finally {
            log.debug("finally执行...");
        }
    }


    private void addBalance() {

        Long userId = 1L;
        BigDecimal addBalance = new BigDecimal("100");

        int updateLineCount = sysUserMapper.addBalance(userId, addBalance);

        //人为制造一个异常
        //int i=1/0;

        log.debug("增加用户余额,修改结果:{}", updateLineCount);
    }
}

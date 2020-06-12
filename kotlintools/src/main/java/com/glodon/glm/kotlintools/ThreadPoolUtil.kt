package com.glodon.glm.kotlintools

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object ThreadPoolUtil {
    /////////////////////////////////////////////////////////////////////
    //////////////  定长线程池                   ////////////////////////
    /////////////////////////////////////////////////////////////////////
    // 1. 创建定长线程池对象 & 设置线程池线程数量固定为3
    var fixedThreadPool: ExecutorService? = null

    //默认固定线程数
    private const val defaultNThreads = 3

    fun setFixedThreadPool(nThreads: Int) {
        fixedThreadPool = Executors.newFixedThreadPool(nThreads)
    }

    /**
     * 特点：只有核心线程 & 不会被回收、线程数量固定、任务队列无大小限制（超出的线程任务会在队列中等待）
     * 应用场景：控制线程最大并发数
     * 具体使用：通过 Executors.newFixedThreadPool() 创建
     */
    fun getFixedThreadPool(): Executor? {
        if (null == fixedThreadPool) {
            fixedThreadPool = Executors.newFixedThreadPool(defaultNThreads)
        }
        return fixedThreadPool
    }

    /////////////////////////////////////////////////////////////////////
    //////////////定时线程池（ScheduledThreadPool ）
    /////////////////////////////////////////////////////////////////////
    //    特点：核心线程数量固定、非核心线程数量无限制（闲置时马上回收）
    //    应用场景：执行定时 / 周期性 任务
    //    使用：通过Executors.newScheduledThreadPool()创建
    // 3. 向线程池提交任务：schedule（）
    //scheduledThreadPool.schedule(task, 1, TimeUnit.SECONDS); // 延迟1s后执行任务
    //scheduledThreadPool.scheduleAtFixedRate(task,10,1000,TimeUnit.MILLISECONDS);//
    // 延迟10ms后、每隔1000ms执行任务
    //
    //// 4. 关闭线程池
    //scheduledThreadPool.shutdown();
    //
    // 1. 创建 定时线程池对象 & 设置线程池线程数量固定为5
    private var scheduledThreadPool: ScheduledExecutorService? = null

    fun getScheduledThreadPool(coreSize:Int): ScheduledExecutorService? {
        if (null == scheduledThreadPool) {
            scheduledThreadPool = Executors.newScheduledThreadPool(coreSize)
        }
        return scheduledThreadPool
    }

    /////////////////////////////////////////////////////////////////////
    //////////////可缓存线程池（CachedThreadPool）
    /////////////////////////////////////////////////////////////////////
//    特点：只有非核心线程、线程数量不固定（可无限大）、灵活回收空闲线程（具备超时机制，全部回收时几乎不占系统资源）、新建线程（无线程可用时）
//
//
//    任何线程任务到来都会立刻执行，不需要等待
//
//
//    应用场景：执行大量、耗时少的线程任务
//    使用：通过Executors.newCachedThreadPool()创建
// 1. 创建可缓存线程池对象

//cachedThreadPool.execute(task);
//
//// 4. 关闭线程池
//cachedThreadPool.shutdown();

//当执行第二个任务时第一个任务已经完成
//那么会复用执行第一个任务的线程，而不用每次新建线程。

    /////////////////////////////////////////////////////////////////////
    //////////////可缓存线程池（CachedThreadPool）
    /////////////////////////////////////////////////////////////////////
    //    特点：只有非核心线程、线程数量不固定（可无限大）、灵活回收空闲线程（具备超时机制，全部回收时几乎不占系统资源）、新建线程（无线程可用时）
    //
    //
    //    任何线程任务到来都会立刻执行，不需要等待
    //
    //
    //    应用场景：执行大量、耗时少的线程任务
    //    使用：通过Executors.newCachedThreadPool()创建
    // 1. 创建可缓存线程池对象
    //cachedThreadPool.execute(task);
    //
    //// 4. 关闭线程池
    //cachedThreadPool.shutdown();
    //当执行第二个任务时第一个任务已经完成
    //那么会复用执行第一个任务的线程，而不用每次新建线程。
    private var cachedThreadPool: ExecutorService? = null

    fun getCachedThreadPool(): ExecutorService? {
        if (null == cachedThreadPool) {
            cachedThreadPool = Executors.newCachedThreadPool()
        }
        return cachedThreadPool
    }

    /////////////////////////////////////////////////////////////////////
    //////////////单线程化线程池（SingleThreadExecutor）
    /////////////////////////////////////////////////////////////////////
    //    特点：只有一个核心线程（保证所有任务按照指定顺序在一个线程中执行，不需要处理线程同步的问题）
    //
    //    应用场景：不适合并发但可能引起IO阻塞性及影响UI线程响应的操作，如数据库操作，文件操作等
    //
    //    使用：通过Executors.newSingleThreadExecutor()创建
    // 1. 创建单线程化线程池
    private var singleThreadExecutor: ExecutorService? = null

    fun getSingleThreadExecutor(): ExecutorService? {
        if (null == singleThreadExecutor) {
            singleThreadExecutor = Executors.newSingleThreadExecutor()
        }
        return singleThreadExecutor
    }


    fun shutdown() {
        fixedThreadPool!!.shutdown()
        fixedThreadPool = null


        scheduledThreadPool!!.shutdown()
        scheduledThreadPool = null

        cachedThreadPool!!.shutdown()
        cachedThreadPool = null

        singleThreadExecutor!!.shutdown()
        singleThreadExecutor = null
    }
}
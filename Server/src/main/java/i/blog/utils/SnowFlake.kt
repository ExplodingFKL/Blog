package i.blog.utils

import i.blog.handlers.exceptions.ExceptionUtils
import org.springframework.stereotype.Component

@Component
class SnowFlake {
    /**
     * 机器标识
     */
    private val workerId: Long = 0

    /**
     * 数据中心编号
     */
    private val dataCenterId: Long = 0

    /**
     * 序列号
     */
    private var sequence = 0L

    /**
     * 上一次的时间戳
     */
    private var lastTimestamp = -1L

    @Synchronized
    fun nextId(): Long {
        var timestamp = timeGen()

        //当前时间小于过去时间,抛出异常表示时间指针被回拨
        if (timestamp < lastTimestamp) {
            throw ExceptionUtils.internalException("时间戳异常")
        }
        //当前时间等于过去时间，表示同一时间出现两个
        if (timestamp == lastTimestamp) {
            sequence = sequence + 1 and MAX_SEQUENCE
            //超出12位长度就会是0，由于运算符（&）的特性，然后重新获取一个时间戳
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            //恢复到初始序列号数
            sequence = 0L
        }
        //赋值为过去的时间戳
        lastTimestamp = timestamp
        return timestamp - INITIAL_TIME shl TIMESTAMP_SHIFT or (dataCenterId shl DATA_CENTER_ID_SHIFT
                ) or (workerId shl WORKER_ID_SHIFT
                ) or sequence
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen()
        }
        return timestamp
    }

    private fun timeGen(): Long {
        return System.currentTimeMillis()
    }

    companion object {
        private const val WORKER_ID_BITS = 5
        private const val DATA_CENTER_ID_BITS = 5
        private const val SEQUENCE_BITS = 12


        private const val INITIAL_TIME = 1288834974657L
        private const val MAX_SEQUENCE = (-1L shl SEQUENCE_BITS).inv()

        /**
         * 所有组成部分移动的距离
         */
        private const val WORKER_ID_SHIFT = SEQUENCE_BITS
        private const val DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS
        private const val TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS
    }
}
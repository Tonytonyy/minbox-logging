/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.logging.client.http.openfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.minbox.framework.logging.client.LoggingConstant;
import org.minbox.framework.logging.client.LogThreadLocal;
import org.minbox.framework.logging.core.MinBoxLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Openfeign Request Interceptor
 * Requests initiated by openfeign carry ApiBoot TraceId and spanId
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-16 16:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingOpenFeignInterceptor implements RequestInterceptor {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingOpenFeignInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        MinBoxLog log = LogThreadLocal.get();
        requestTemplate.header(LoggingConstant.HEADER_NAME_TRACE_ID, log.getTraceId());
        requestTemplate.header(LoggingConstant.HEADER_NAME_PARENT_SPAN_ID, log.getSpanId());
        logger.debug("RequestUri：{}, Method：{}，Setting Logging TraceId：{}，SpanId：{} With Openfeign.",
                requestTemplate.url(), requestTemplate.request().httpMethod().toString(), log.getTraceId(), log.getSpanId());
    }
}

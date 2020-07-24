//package com.xingkong.spingboot.commonutil;
//
//import com.alibaba.fastjson.JSON;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * * @className: HttpRequestFilter
// * * @description: 请求参数监听（入参）
// * * @author: fanxiaoping
// * * @date: 2020-07-24  15:04
// **/
//@Component
//@AllArgsConstructor
//@Slf4j
//public class RequestFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        HttpMethod method = request.getMethod();
//        String contentType = request.getHeaders().getFirst("Content-Type");
//        if(HttpMethod.POST.equals(method)){
//            return DataBufferUtils.join(exchange.getRequest().getBody())
//                    .flatMap(dataBuffer -> {
//                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(bytes);
//                        try {
//                            String bodyString = new String(bytes, "utf-8");
//                            log.info(bodyString);//打印请求参数
//                            exchange.getAttributes().put("POST_BODY", bodyString);
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                        DataBufferUtils.release(dataBuffer);
//                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
//                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//                            return Mono.just(buffer);
//                        });
//
//                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
//                                exchange.getRequest()) {
//                            @Override
//                            public Flux<DataBuffer> getBody() {
//                                return cachedFlux;
//                            }
//                        };
//                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
//                    });
//        }
//        if(HttpMethod.GET.equals(method)){
//            MultiValueMap<String, String> queryParams = request.getQueryParams();
//            String params = JSON.toJSONString(queryParams);
//            log.info(params);
//            return chain.filter(exchange);
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -99;
//    }
//}

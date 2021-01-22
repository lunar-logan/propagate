package org.propagate.rest.handler;

//@Component
//@AllArgsConstructor
//public class GetAllFeatureFlagsHandler implements HandlerFunction<ServerResponse> {
//    private final FeatureFlagService service;
//
//    @Override
//    public Mono<ServerResponse> handle(ServerRequest request) {
//        return service.getAllFeatureFlags()
//                .log()
//                .flatMap(ff -> ServerResponse.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE))
//                .collectList()
//                .log()
//                .flatMap(ffs -> ServerResponse.ok().bodyValue(ffs));
//    }
//}

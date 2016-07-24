/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.reactor.client.v3.processes;

import org.cloudfoundry.client.v3.processes.GetProcessRequest;
import org.cloudfoundry.client.v3.processes.GetProcessResponse;
import org.cloudfoundry.client.v3.processes.GetProcessStatisticsRequest;
import org.cloudfoundry.client.v3.processes.GetProcessStatisticsResponse;
import org.cloudfoundry.client.v3.processes.ListProcessesRequest;
import org.cloudfoundry.client.v3.processes.ListProcessesResponse;
import org.cloudfoundry.client.v3.processes.Processes;
import org.cloudfoundry.client.v3.processes.ScaleProcessRequest;
import org.cloudfoundry.client.v3.processes.ScaleProcessResponse;
import org.cloudfoundry.client.v3.processes.TerminateProcessInstanceRequest;
import org.cloudfoundry.client.v3.processes.UpdateProcessRequest;
import org.cloudfoundry.client.v3.processes.UpdateProcessResponse;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.v3.AbstractClientV3Operations;
import reactor.core.publisher.Mono;

/**
 * The Reactor-based implementation of {@link Processes}
 */
public final class ReactorProcesses extends AbstractClientV3Operations implements Processes {

    /**
     * Creates an instance
     *
     * @param connectionContext the {@link ConnectionContext} to use when communicating with the server
     * @param root              the root URI of the server.  Typically something like {@code https://api.run.pivotal.io}.
     * @param tokenProvider     the {@link TokenProvider} to use when communicating with the server
     */
    public ReactorProcesses(ConnectionContext connectionContext, Mono<String> root, TokenProvider tokenProvider) {
        super(connectionContext, root, tokenProvider);
    }

    @Override
    public Mono<GetProcessResponse> get(GetProcessRequest request) {
        return get(request, GetProcessResponse.class, builder -> builder.pathSegment("v3", "processes", request.getProcessId()));
    }

    @Override
    public Mono<GetProcessStatisticsResponse> getStatistics(GetProcessStatisticsRequest request) {
        return get(request, GetProcessStatisticsResponse.class, builder -> builder.pathSegment("v3", "processes", request.getProcessId(), "stats"));
    }

    @Override
    public Mono<ListProcessesResponse> list(ListProcessesRequest request) {
        return get(request, ListProcessesResponse.class, builder -> builder.pathSegment("v3", "processes"));
    }

    @Override
    public Mono<ScaleProcessResponse> scale(ScaleProcessRequest request) {
        return put(request, ScaleProcessResponse.class, builder -> builder.pathSegment("v3", "processes", request.getProcessId(), "scale"));
    }

    @Override
    public Mono<Void> terminateInstance(TerminateProcessInstanceRequest request) {
        return delete(request, Void.class, builder -> builder.pathSegment("v3", "processes", request.getProcessId(), "instances", request.getIndex()));
    }

    @Override
    public Mono<UpdateProcessResponse> update(UpdateProcessRequest request) {
        return patch(request, UpdateProcessResponse.class, builder -> builder.pathSegment("v3", "processes", request.getProcessId()));
    }

}
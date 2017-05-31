package io.sited.message.api;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.http.QueryParam;
import io.sited.message.api.message.BatchDeleteRequest;
import io.sited.message.api.message.BatchReadRequest;
import io.sited.message.api.message.CountMessageResponse;
import io.sited.message.api.message.MessageQuery;
import io.sited.message.api.message.MessageRequest;
import io.sited.message.api.message.MessageResponse;

/**
 * @author chi
 */
public interface MessageWebService {
    @Path("/api/message/:id")
    @PUT
    MessageResponse get(@PathParam("id") String id);

    @Path("/api/message/find")
    @PUT
    FindView<MessageResponse> find(MessageQuery query);

    @Path("/api/message/count")
    @GET
    CountMessageResponse count(@QueryParam("to") String to);

    @Path("/api/message")
    @POST
    MessageResponse create(MessageRequest request);

    @Path("/api/message/:id/read")
    @POST
    void read(@PathParam("id") String id);

    @Path("/api/message/batch-read")
    @POST
    void batchRead(BatchReadRequest request);

    @Path("/api/message/batch-delete")
    @POST
    void batchDelete(BatchDeleteRequest request);

    @Path("/api/message/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}

package com.spit.tph.WebService;

import com.spit.tph.Entity.PhotoAPIResponse;
import com.spit.tph.Request.DisposalAssetsRequest;
import com.spit.tph.Request.ReturnBorrowedAssetRequest;
import com.spit.tph.Request.UpdateWaitingListRequest;
import com.spit.tph.Request.UploadStockTakeRequest;
import com.spit.tph.Response.APIResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    //@Headers("Content-Type: application/json")
    @GET("updateAssetEpc.php")
    Call<APIResponse> modifyAsset(@Query("id") String id, @Query("epc") String epc);

    @POST("borrowAssets.php")
    Call<APIResponse> borrowAsset(@Body UpdateWaitingListRequest body);

    @POST("stockTakeAssets.php")
    Call<APIResponse> stockTakeAssets(@Body UploadStockTakeRequest body);

    @POST("returnBorrowedAssets.php")
    Call<APIResponse> returnAsset(@Body ReturnBorrowedAssetRequest body);

    @GET("disposalAsset.php")
    Call<APIResponse> disposalAsset(@Query("id") String id);

    @POST("disposalAssets.php")
    Call<APIResponse> disposalAssets(@Body DisposalAssetsRequest body);

    @Multipart
    @POST("uploadimage.php")
    Call<PhotoAPIResponse> uploadImage(@Part MultipartBody.Part image);
}

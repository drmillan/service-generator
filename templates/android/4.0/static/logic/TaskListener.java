package {{{staticPackage}}}.logic;

import {{{staticPackage}}}.utils.ResponseInfo;

/**
 * Generated class, do not touch
 * Created by Mobivery
 */
public interface TaskListener<T,Q> {
    /**
	 * Task completed callback (with ResponseInfo)
	 * @param request RequestDTO
	 * @param response ResponseDTO
     * @param responseInfo Response metadata (to get http header errors)
     */
    public void onServiceResponse(T request,Q response,ResponseInfo responseInfo);
    /**
     * Task error callback
     * @param request RequestDTO
     * @param ex ResponseDTO
     */
    public void onServiceError(T request,Exception ex);
}

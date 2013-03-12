package com.mobivery.logic;

public interface ServiceExceptionListener {
	/**
	 * Notificacion en caso de error de comunicaciones
	 * @param ex Excepcion recibida en el servicio
	 */
	void onServiceError(ServiceException ex);
}

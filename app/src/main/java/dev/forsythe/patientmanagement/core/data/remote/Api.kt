package dev.forsythe.patientmanagement.core.data.remote

import dev.forsythe.patientmanagement.core.data.remote.model.request.AddVisitRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.AddVitalsRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.LogInRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.RegisterRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.SignUpRequest
import dev.forsythe.patientmanagement.core.data.remote.model.request.ViewVisitsRequest
import dev.forsythe.patientmanagement.core.data.remote.model.response.AddVisitResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.AddVisitResponseData
import dev.forsythe.patientmanagement.core.data.remote.model.response.AddVitalsResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.AddVitalsResponseData
import dev.forsythe.patientmanagement.core.data.remote.model.response.Data
import dev.forsythe.patientmanagement.core.data.remote.model.response.GetPatientsResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.LogInData
import dev.forsythe.patientmanagement.core.data.remote.model.response.LogInResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.PatientData
import dev.forsythe.patientmanagement.core.data.remote.model.response.RegisterResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.SignUpResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.ViewPatientResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.ViewVisitsResponse
import dev.forsythe.patientmanagement.core.data.remote.model.response.ViewVisitsResponseData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import timber.log.Timber


private const val TAG = "Api"
suspend fun KtorClient.signUp(
    request: SignUpRequest,
): Data {

    return try {
        val response: SignUpResponse = client.post {
            url("user/signup")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        if (response.success){
        Timber.tag(TAG).d("signUp: ${response.message}")
            response.data
        }else{
            Timber.tag(TAG).e("Error singing up: ${response.message}")
            throw Exception(response.message)
        }
    } catch (e: Exception) {
        //error log
        Timber.tag(TAG).e(e)
        throw e
    }

}

suspend fun  KtorClient.logIn(
    request: LogInRequest
) : LogInData {

    return try {
        val response : LogInResponse =  client.post {
            url("user/signin")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        if (response.success){
            Timber.tag(TAG).d("logIn: ${response.message}")
            response.data
        } else {
            Timber.tag(TAG).e("Error loging in: ${response.message}")
            throw Exception(response.message)
        }
    } catch ( e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }
}

suspend fun KtorClient.registerPatient(
    request : RegisterRequest,
    token : String
) : Data{
    return try {
        val response  : RegisterResponse = client.post {
            url("patients/register")
            contentType(ContentType.Application.Json)
            setBody(request)
            header("Authorization", "Bearer $token")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("registerPatient: ${response.message}")
            response.data
        } else {
            Timber.tag(TAG).e("Error registering patient: ${response.message}")
            throw Exception(response.message)
        }
    } catch (e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }
}


suspend fun KtorClient.fetchPatientsList(
    token: String
) :  List<PatientData>{

    return try {
        val response : GetPatientsResponse = client.get {
            url("patients/view")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token}")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("fetchPatientsList: ${response.message}")
            response.data
        } else {
            Timber.tag(TAG).e("Error fetching patients list: ${response.message}")
            throw Exception(response.message)
        }

    }catch (e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }
}

suspend fun KtorClient.fetchPatient(
    token: String,
    patientId : String
) : List<PatientData> {

    return try {
        val response :  ViewPatientResponse = client.get {
            url("patients/show/$patientId")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("fetchPatient: ${response.message}")
            response.data
        }else {
            Timber.tag(TAG).e("Error fetching patient: ${response.message}")
            throw Exception(response.message)
        }

    }catch (e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }
}

suspend fun KtorClient.addVital(
    token: String,
    request: AddVitalsRequest
) : AddVitalsResponseData{

    return try {
        val response :  AddVitalsResponse= client.post {
            url("vital/add")
            contentType(ContentType.Application.Json)
            setBody(request)
            header("Authorization", "Bearer $token")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("addVital: ${response.message}")
            response.data
        }else {
            Timber.tag(TAG).e("Error adding vital: ${response.message}")
            throw Exception(response.message)
        }

    }catch ( e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }
}


suspend fun KtorClient.addVisit(
    request : AddVisitRequest,
    token: String
) : AddVisitResponseData{
    return try {
        val response : AddVisitResponse = client.post {
            url("visits/add")
            contentType(ContentType.Application.Json)
            setBody(request)
            header("Authorization", "Bearer $token")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("addVisit: ${response.message}")
            response.data
        } else {
            Timber.tag(TAG).e("Error adding visit: ${response.message}")
            throw Exception(response.message)
        }

    } catch (e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }

}

suspend fun KtorClient.fetchVisits(
    token: String,
    request :  ViewVisitsRequest
) :  List<ViewVisitsResponseData> {
    return  try {
        val response :  ViewVisitsResponse = client.post {
            url("visits/view")
            contentType(ContentType.Application.Json)
            setBody(request)
            header("Authorization", "Bearer $token")
        }.body()

        if (response.success){
            Timber.tag(TAG).d("fetchVisits: ${response.message}")
            response.data
        }else{
            Timber.tag(TAG).e("Error fetching visits: ${response.message}")
            throw Exception(response.message)
        }
    } catch (e : Exception){
        Timber.tag(TAG)
            .e(e)
        throw e
    }

}
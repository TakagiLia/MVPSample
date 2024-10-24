package biz.takagi.mvpsample.repository

import biz.takagi.mvpsample.model.PersonInfoModel

class MainRepository {

    fun getPersonalData(id :String) : PersonInfoModel {
        return if (id == "1234"){
            PersonInfoModel(
                true,
                "","",""
            )
        }else{
            PersonInfoModel(
                false,
                "","",""
            )
        }
    }
}
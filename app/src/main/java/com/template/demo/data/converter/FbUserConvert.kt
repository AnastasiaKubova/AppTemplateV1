package com.template.demo.data.converter

import com.template.basecomponents.converters.FbConverter
import com.template.demo.cache.preference.PreferenceRepository
import com.template.demo.data.repository.firebase.data.FbUserData
import com.template.demo.domain.data.UserModel
import java.util.Calendar

class FbUserConvert(
    private val preference: PreferenceRepository
): FbConverter<FbUserData, UserModel> {

    override fun convert(id: String, data: FbUserData): UserModel {
        return UserModel(
            id,
            data.name,
            data.email,
            data.password,
            Calendar.getInstance().also { c ->
                data.birthday?.let { c.timeInMillis = it }
            },
            preference.getTheme()
        )
    }
}
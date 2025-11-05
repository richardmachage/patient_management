package dev.forsythe.patientmanagement.feature.assessment

import dev.forsythe.patientmanagement.core.model.GeneralHealth

data class AssessmentScreenState(
    val isLoading: Boolean = false,
    val patientName: String = "Loading...",
    val title: String = "Assessment",
    val assessmentType: AssessmentType = AssessmentType.UNKNOWN,

    // Form fields
    val visitDate: String = "",
    val generalHealth: GeneralHealth? = null, // "Good" or "Poor"
    val comments: String = "",

    //Conditional Fields
    // Only one of these will be shown by the UI at a time
    val onDiet: Boolean = false,
    val onDrugs: Boolean = false,

    //Error Messages ---
    val visitDateError: String? = null,
    val generalHealthError: String? = null,
    val conditionalQuestionError: String? = null, // One error for whichever question is visible
    val saveError: String? = null,

    //Navigation
    val isSaveSuccessful: Boolean = false
)
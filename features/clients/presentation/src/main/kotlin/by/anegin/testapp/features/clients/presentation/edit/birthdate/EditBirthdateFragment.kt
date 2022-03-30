package by.anegin.testapp.features.clients.presentation.edit.birthdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentEditClientBirthdateBinding
import by.anegin.testapp.features.clients.presentation.edit.EditClientViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal class EditBirthdateFragment : Fragment(R.layout.fragment_edit_client_birthdate) {

    private val viewModel: EditClientViewModel by viewModels({ requireParentFragment() })

    private var binding: FragmentEditClientBirthdateBinding? = null

    private var selectedDate: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentEditClientBirthdateBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {

            editBirthdate.setOnClickListener {
                showDatePicker()
            }
            inputBirthdate.setOnClickListener {
                showDatePicker()
            }

            viewModel.client.observe(viewLifecycleOwner) { client ->
                if (client.dateOfBirth.time > 0) {
                    selectedDate = client.dateOfBirth
                    editBirthdate.setText(formateBirthdate(client.dateOfBirth))
                }
            }
        }
    }

    private fun showDatePicker() {
        val utcTimeZone = TimeZone.getTimeZone("UTC")
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val maxDate = Calendar.getInstance(utcTimeZone)
        maxDate.timeInMillis = today

        val minDate = Calendar.getInstance(utcTimeZone)
        minDate.timeInMillis = today
        minDate.add(Calendar.YEAR, -120)
        minDate.set(Calendar.MONTH, Calendar.JANUARY)
        minDate.set(Calendar.DAY_OF_MONTH, 1)

        val openAt = selectedDate?.let {
            Calendar.getInstance(utcTimeZone).apply {
                timeInMillis = it.time
            }
        }

        // 30: 1648598400000
        // 29: 1648512000000
        // XX: 1648425600000

        val constraints = CalendarConstraints.Builder()
            .setStart(minDate.timeInMillis)
            .setEnd(maxDate.timeInMillis)
            .apply {
                openAt?.let { setOpenAt(it.timeInMillis) }
            }
            .setValidator(DateValidatorPointBackward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.date_of_birth_full)
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .apply {
                selectedDate?.let { setSelection(it.time) }
            }
            .setCalendarConstraints(constraints)
            .build()

        datePicker.show(childFragmentManager, null)
        // 1648512000000 (29)
        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            this.selectedDate = Date(selectedDate).also {
                viewModel.setBirthdate(it)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun formateBirthdate(date: Date): String {
        return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
            .format(date)
    }
}
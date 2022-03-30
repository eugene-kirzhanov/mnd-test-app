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

class EditBirthdateFragment : Fragment(R.layout.fragment_edit_client_birthdate) {

    private val viewModel: EditClientViewModel by viewModels({ requireParentFragment() })

    private var binding: FragmentEditClientBirthdateBinding? = null

    private var currentDate = Date(System.currentTimeMillis())

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
                    currentDate = client.dateOfBirth
                    editBirthdate.setText(formateBirthdate(client.dateOfBirth))
                }
            }
        }
    }

    private fun showDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val maxDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        maxDate.timeInMillis = today

        val minDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        minDate.timeInMillis = today
        minDate.add(Calendar.YEAR, -100)

        val constraints = CalendarConstraints.Builder()
            .setStart(minDate.timeInMillis)
            .setEnd(maxDate.timeInMillis)
            .setOpenAt(currentDate.time)
            .setValidator(DateValidatorPointBackward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.date_of_birth_full)
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setSelection(currentDate.time)
            .setCalendarConstraints(constraints)
            .build()

        datePicker.show(childFragmentManager, null)

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            currentDate = Date(selectedDate)
            viewModel.setBirthdate(currentDate)
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
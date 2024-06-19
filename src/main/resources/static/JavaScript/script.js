document.addEventListener('DOMContentLoaded', function() {
    const formTemplate = document.getElementById('formTemplate').content;
    const formFields1 = document.querySelector('#formFields1');
    const formFields2 = document.querySelector('#formFields2');

    // Clone the form template for both forms
    formFields1.appendChild(document.importNode(formTemplate, true));
    formFields2.appendChild(document.importNode(formTemplate, true));

    const auswahlForm = document.getElementById('auswahl');
    const auswahl1 = document.getElementById('auswahl1');
    const auswahl2 = document.getElementById('auswahl2');

    // Event listener to toggle forms
    auswahlForm.addEventListener('change', function(event) {
        if (event.target.name === 'toggle') {
            const value = event.target.value;
            auswahl1.classList.toggle('hidden', value !== 'option1');
            auswahl2.classList.toggle('hidden', value !== 'option2');
        }
    });

    // Validation function
    function validateForm(formData, formId) {
        let isValid = true;
        let errorMessages = '';

        if (formId === 'contractForm2' && formData.get('vsnr').trim() === '') {
            errorMessages += 'Versicherungsnummer muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('vorname').trim() === '') {
            errorMessages += 'Vorname muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('nachname').trim() === '') {
            errorMessages += 'Nachname muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('geburtsdatum').trim() === '') {
            errorMessages += 'Geburtsdatum muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('addresse').trim() === '') {
            errorMessages += 'Addresse muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('versicherungsbeginn').trim() === '') {
            errorMessages += 'Versicherungsbeginn muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('fahrzeug_hersteller').trim() === '') {
            errorMessages += 'Fahrzeughersteller muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('fahrzeug_typ').trim() === '') {
            errorMessages += 'Fahrzeugtyp muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('fahrzeug_hoechstgeschwindigkeit').trim() === '') {
            errorMessages += 'Höchstgeschwindigkeit muss ausgefüllt werden.<br>';
            isValid = false;
        }

        if (formData.get('amtliches_kennzeichen').trim() === '') {
            errorMessages += 'Amtliches Kennzeichen muss ausgefüllt werden.<br>';
            isValid = false;
        }

        // Display error messages in the correct error container
        const errorContainer = document.getElementById(`errorMessages${formId.slice(-1)}`);
        errorContainer.innerHTML = errorMessages;

        return isValid;
    }

    // Submit form function
    function submitForm(event, formId) {
        event.preventDefault();

        const formData = new FormData(document.getElementById(formId));
        const vsnr = formData.get('vsnr');

        if (!validateForm(formData, formId)) {
            return false;
        }

        let apiUrl = 'http://localhost:8089/vertrag';
        let method = 'POST';
        let successMessage = 'Vertrag erfolgreich gespeichert!';

        if (formId === 'contractForm2') {
            apiUrl += '/change';
            method = 'PUT';
            successMessage = 'Vertrag erfolgreich geändert!';
        } else {
            apiUrl += '/new';
        }

        fetch(apiUrl, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(Object.fromEntries(formData))
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Netzwerkantwort war nicht okay');
                }
                return response.json();
            })
            .then(data => {
                console.log('Erfolg:', data);
                alert(successMessage);
                clearErrorMessages(formId);
                document.getElementById(formId).reset();
            })
            .catch(error => {
                console.error('Fehler:', error);
                displayErrorMessage(formId, 'Fehler beim Speichern des Vertrags. Bitte versuchen Sie es erneut.');
            });
    }

    // Function to display error message
    function displayErrorMessage(formId, message) {
        const errorContainer = document.getElementById(`errorMessages${formId.slice(-1)}`);
        errorContainer.innerHTML = `<p style="color: red;">${message}</p>`;
    }

    // Function to clear error messages
    function clearErrorMessages(formId) {
        const errorContainer = document.getElementById(`errorMessages${formId.slice(-1)}`);
        errorContainer.innerHTML = '';
    }

    // Event listeners for form submissions
    document.getElementById('contractForm1').addEventListener('submit', function(event) {
        submitForm(event, 'contractForm1');
    });

    document.getElementById('contractForm2').addEventListener('submit', function(event) {
        submitForm(event, 'contractForm2');
    });

});

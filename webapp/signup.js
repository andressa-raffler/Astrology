class SignUpForm {
    constructor() {
      this.apiUrl = "http://localhost:9090/astrology/v1/user";
      this.signUpButton = document.getElementById('signUp');
      this.validationButton = document.getElementById('sendValidationCode');
      this.modal = document.getElementById('modal');
      this.closeButton = this.modal.querySelector('.close');
      this.inputName = document.querySelector('.name');
      this.inputEmail = document.querySelector('.email');
      this.inputPassword = document.querySelector('.password');
      this.inputValidationCode = document.getElementById('validationCode');
  
      this.signUpButton.addEventListener('click', this.signUp.bind(this));
      this.validationButton.addEventListener('click', this.sendValidationCode.bind(this));
      this.closeButton.addEventListener('click', this.closeModal.bind(this));
    }
  
    signUp(event) {
      event.preventDefault();
  
      const requestData = {
        name: this.inputName.value,
        email: this.inputEmail.value,
        password: this.inputPassword.value
      };
  
      fetch(`${this.apiUrl}/sign-up`, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(requestData)
      })
        .then(response => response.json())
        .then(data => {
          if (!data.message.includes("Error")) {
            this.openModal();
          }else{
            alert(data.message);
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }
  
    sendValidationCode(event) {
      event.preventDefault();
  
      const validationCode = this.inputValidationCode.value;
  
      fetch(`${this.apiUrl}/email_validate`, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify({ validationCode })
      })
        .then(response => response.json())
        .then(data => {
          if (data.message === "OK") {
            this.closeModal();
          } else {
            const errorElement = document.createElement('p');
            errorElement.textContent = data.message;
            const modalContent = document.querySelector('.modal-content');
            modalContent.appendChild(errorElement);
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
    }
  
    openModal() {
        document.getElementById('modal').classList.add('active')
    }
  
    closeModal() {
      document.getElementById('modal').classList.remove('active')
      this.inputValidationCode.value = '';
      const modalContent = document.querySelector('.modal-content');
      modalContent.innerHTML = '';
    }
  }
  
  const signUpForm = new SignUpForm();
  
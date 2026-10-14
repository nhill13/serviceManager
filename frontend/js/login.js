// This file executes automatically after the DOM parses due to 'defer'
const loginForm = document.getElementById('loginForm');

loginForm.addEventListener('submit', async function(event) {
	// Prevent page refresh
	event.preventDefault();

	// Grab the current input values
	const usernameInput = document.getElementById('username').value;
	const passwordInput = document.getElementById('password').value;

	const loginData = {
		username: usernameInput,
		password: passwordInput
	};

	try {
		const response = await fetch('https://servicemanager/api/login', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(loginData)
		});

		if (response.ok) {
			const result = await response.json();
			handleLoginSuccess(result);
		} else {
			handleLoginFailure(response.status);
		}
	} catch (error) {
		console.error('Network error during login:', error);
	}
});

// Helper functions to keep code clean
function handleLoginSuccess(data) {
	console.log('Success:', data);
	// Example: redirect to dashboard
	// window.location.href = '/dashboard.html';
}

function handleLoginFailure(status) {
	console.error('Failure status code:', status);
	alert('Invalid username or password.');
}


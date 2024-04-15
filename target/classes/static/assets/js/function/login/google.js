function decodeJwtResponse(credential) {
  const payloadBase64 = credential.split(".")[1];
  const decodedPayload = JSON.parse(atob(payloadBase64));
  return decodedPayload;
}
function handleCredentialResponse(response) {
  const url = "https://localhost:443/googleform";
  const responsePayload = decodeJwtResponse(response.credential);

  const user = {
    userName: responsePayload.sub,
    email: responsePayload.email,
    fullName: responsePayload.name,
  };

  const optionParameters = {
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
    method: "POST",
  };
  fetch(url, optionParameters)
    .then((user) => {
      return user.json();
    })
    .then((response) => {
      const encode = btoa(JSON.stringify(responsePayload.sub));
      localStorage.setItem("token", encode);
    })
    .catch((error) => console.log(error));
}
import { useEffect } from "react";
import { getAllContacts } from "./api/contactApi";
import ContactsPage from "./pages/ContactsPage";

function App() {
  return <ContactsPage />;
}

export default App;

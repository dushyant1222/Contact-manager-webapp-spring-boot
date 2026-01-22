import { useEffect, useState } from "react";
import { getAllContacts, deleteContact } from "../api/contactApi";
import ContactForm from "../components/ContactForm";


function ContactsPage(){
    const[contacts, setContacts] = useState([]);
    const [editingContact, setEditingContact] = useState(null);
    const[search, setSearch] = useState("");
    const [loading, setLoading] = useState(true);



    useEffect(()=> {loadcontacts();},[]);

    const loadcontacts = () => {
        setLoading(true);
        getAllContacts().then((response) => {setContacts(response.data);}).catch((error) => {
            console.error("Failed to load the contacts", error);
        });

    };

    const handleDelete = (id) => {
        deleteContact(id).then(() => {loadcontacts();}).catch((error) =>{
            console.error("Failed to delete", error)
        });
    };
    return (
  <div className="app-container">
    <ContactForm
      onSuccess={() => {
        loadContacts();
        setEditingContact(null);
      }}
      initialData={editingContact}
      onCancel={() => setEditingContact(null)}
    />

    <div className="search-section">
      <h3>Search</h3>
      <input
        type="text"
        placeholder="Search by name or email"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
    </div>

    <h2 className="contacts-title">Contacts</h2>

    {contacts.length === 0 ? (
      <p>No contacts found</p>
    ) : (
      <div className="contacts-list">
        {contacts
          .filter(contact =>
            (contact.fullName &&
              contact.fullName.toLowerCase().includes(search.toLowerCase())) ||
            (contact.email &&
              contact.email.toLowerCase().includes(search.toLowerCase()))
          )
          .map(contact => (
            <div className="contact-card" key={contact.id}>
              <div className="contact-info">
                <strong>{contact.fullName || contact.email}</strong>
                <span>{contact.phone}</span>
              </div>

              <div className="contact-actions">
                <button onClick={() => handleDelete(contact.id)} className="danger">
                  Delete
                </button>
                <button onClick={() => setEditingContact(contact)}>
                  Edit
                </button>
              </div>
            </div>
          ))}
      </div>
    )}
  </div>
);

}
export default ContactsPage;


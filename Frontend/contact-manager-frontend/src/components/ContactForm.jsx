import { useEffect, useState } from "react";
import { createcontact, updateContact } from "../api/contactApi";

function ContactForm({onSucess, initialData, oncancel}){
    const [formData, setFormData] = useState ({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        tags: ""
    });

    const [error, setError] = useState(null);

    //here prefill the form when editing
    useEffect(() => {
        if(initialData){
            setFormData({
                firstName: initialData.firstName || "",
                lastName: initialData.lastName || "",
                email: initialData.email || "",
                phone: initialData.phone || "",
                tags: initialData.tags || ""
            });
        }
    }, [initialData]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setError(null);

        const apiCall = initialData ? updateContact(initialData.id, formData) : createcontact(formData);


        apiCall.then(() =>{
            setFormData({
                firstName: "",
                lastName: "",
                email: "",
                phone: "",
                tags: ""
            });
            onSucess(); //this will reaload the conats
        })
        .catch((err) => {
            setError(err.response?.data?.message || "Operation is failed" ) ;

        });
    };
    
    return(

        <form onSubmit={handleSubmit}>
            <h3>{initialData ? "Edit Contact" : "Add Contact"}</h3>


            <input
            name = "firstName"
            placeholder="First Name"
            value={formData.firstName}
            onChange={handleChange}
            />

            <input
            name="lastName"
            placeholder="Last Name"
            value={formData.lastName}
            onChange={handleChange}
            />

            <input
            name='email'
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            />

            <input
            name="phone"
            placeholder="Phone Number"
            value={formData.phone}
            onChange={handleChange}
            />

            <input
            name = "tags"
            placeholder="Tags"
            value={formData.tags}
            onChange={handleChange}
            />
            <button type="submit">{initialData ? "Update" : "Save"}</button>
            {initialData && (<button type="button" onClick={oncancel}>Cancel</button>)}
            

            {error && <p style={{color:"red"}}>{error}</p>}

        </form>

    );
}
export default ContactForm;
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Container from '@mui/material/Container';
import { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import { studentApi } from "../api/api";

export default function Student() {
    const paperStyle={padding:'50px 20px', width:600, margin:"20px auto"}
    const[name, setName] = useState('');
    const[address, setAddress] = useState('');
    const[student, setStudent]  = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);
    const [editingId, setEditingId] = useState(null);
    const [q, setQ] = useState("");
    const [page, setPage] = useState(0);
    const [size] = useState(5);
    const [totalPages, setTotalPages] = useState(0);


   const handleSubmit = async (e) => {
     e.preventDefault();
     setSuccess("");
     setError("");

     try {
       setLoading(true);

       if (editingId == null) {
         await studentApi.create({ name, address });
         setSuccess("Student added");
       } else {
         await studentApi.update(editingId, { name, address });
         setSuccess("Student updated");
       }

       setName("");
       setAddress("");
       setEditingId(null);

       await loadStudents({ nextPage: 0, nextQ: q });
     } catch (e) {
       setError(e.message || "Operation failed");
     } finally {
       setLoading(false);
     }
   };

    const loadStudents = async ({ nextPage = page, nextQ = q } = {}) => {
      try {
        setLoading(true);
        setError("");
        const result = await studentApi.list({ page: nextPage, size, q: nextQ, sort: "id,desc" });

        setStudent(result.content ?? []);
        setTotalPages(result.totalPages ?? 0);
        setPage(result.number ?? nextPage);
      } catch (e) {
        setError(e.message || "Failed to load students");
      } finally {
        setLoading(false);
      }
    };


    useEffect(() => {
        loadStudents({ nextPage: 0, nextQ: "" });
    }, []);

    return (
    <Container>
        <Paper elevation={3} style={paperStyle}>
          <h1 style={{color:"blue"}}>Add Student</h1>

          {loading && <p>Loading...</p>}
          {error && <p style={{ color: "red" }}>{error}</p>}
          {success && <p style={{ color: "green" }}>{success}</p>}

        <Box
          component="form"
           onSubmit={handleSubmit}
          sx={{ '& > :not(style)': { m: 1, width: '25ch' } }}
          noValidate
          autoComplete="off"
        >
      <TextField
      id="outlined-basic"
      label="Student Name"
      variant="outlined"
      fullWidth
      value = {name}
      onChange = {(e)=>setName(e.target.value)}
      />

      <TextField
       id="outlined-basic"
       label="Student Address"
       variant="outlined"
       fullWidth
       value = {address}
       onChange = {(e)=>setAddress(e.target.value)}
       />


       {/*submit button uses type = "submit" */}
         <Button variant="contained" type="submit" disabled={loading}>
                  {editingId != null ? "Save" : "Submit"}
         </Button>



       {/* Cancel Edit goes RIGHT HERE */}
        {editingId != null && (
          <Button
            type="button"
            variant="outlined"
            disabled={loading}
            onClick={() => {
              setEditingId(null);
              setName("");
              setAddress("");
              setError("");
              setSuccess("");
            }}
            sx={{ ml: 1 }}
          >
            Cancel
          </Button>
        )}

      </Box>
    </Paper>

    <h1>Student List</h1>

    <TextField
         label="Search (name or address)"
         variant="outlined"
         fullWidth
         value={q}
         onChange={(e) => setQ(e.target.value)}
       />
    <Button
    variant="outlined"
    disabled={loading}
    onClick={() => loadStudents({ nextPage: 0, nextQ: q })}
     >
     Search
      </Button>
        <Button
           variant="text"
           disabled={loading}
           onClick={() => {
           setQ("");
           loadStudents({ nextPage: 0, nextQ: "" });
                   }}
          >
          Reset
         </Button>

    <Paper elevation={3} style = {paperStyle}>

    {student.map((s) => (
      <Paper
        elevation={6}
        style={{ margin: "10px", padding: "15px", textAlign: "left" }}
        key={s.id}
      >
        Id : {s.id} <br />
        Name : {s.name} <br />
        Address : {s.address}

        <div style={{ marginTop: "10px" }}>
          <button
            onClick={() => {
              // Edit: fill the form with this student's values
              setEditingId(s.id);
              setName(s.name);
              setAddress(s.address);
              setError("");
              setSuccess("");
            }}
          >
            Edit
          </button>

          <button
            style={{ marginLeft: "10px" }}
            onClick={async () => {
              setError("");
              setSuccess("");
              try {
                setLoading(true);
                await studentApi.remove(s.id);
                setSuccess("Student deleted");

                await loadStudents({ nextPage: page, nextQ: q });
              } catch (e) {
                setError(e.message || "Failed to delete");
              } finally {
                setLoading(false);
              }
            }}
          >
            Delete
          </button>
        </div>
      </Paper>
    ))}


        <div style={{ display: "flex", gap: "10px", alignItems: "center", marginBottom: "10px" }}>
          <Button
            variant="outlined"
            disabled={loading || page <= 0}
            onClick={() => loadStudents({ nextPage: page - 1, nextQ: q })}
          >
            Prev
          </Button>

          <span>
            Page {page + 1} / {totalPages || 1}
          </span>

          <Button
            variant="outlined"
            disabled={loading || page + 1 >= totalPages}
            onClick={() => loadStudents({ nextPage: page + 1, nextQ: q })}
          >
            Next
          </Button>
        </div>
        

    </Paper>

    </Container>
  )
}


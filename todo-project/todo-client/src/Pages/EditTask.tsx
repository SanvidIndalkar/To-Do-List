import { Box, Button, TextField } from "@mui/material";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const EditTask = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const queryClient = useQueryClient();
    
    const [task, setTask] = useState(location.state);
    
    const {id, taskStatus, taskDescription, taskTitle} = task;
    const updatedTask = useState({
        id, taskDescription, taskStatus, taskTitle}
    );
    const handleInputChange = (e) => {
        const {name, value} = e.target;
        console.log(name + " " + value)
        setTask((t) => ({
            ...t,
            [name] : value
        }))
    }

    const mutation = useMutation({
        mutationFn : () => axios.put("http://localhost:8080/tasks/update/" + id, task),
        // onSuccess: () => {
             
             
        // }   
    })

    const handleSubmit = (e) => {
        e.preventDefault();
        mutation.mutate();
    }
    
    React.useEffect(() => {
        if (mutation.isSuccess) {
            queryClient.invalidateQueries({
                queryKey : ["alltasks"]
            });
            navigate("/");
            mutation.reset();
        }
    });
    
    return (
        <>
            <Box
        display="flex"
        alignContent="center"
        justifyContent="center"
        margin="16rem">
        <form onSubmit={handleSubmit}>
            <Box display="flex"
            alignContent="center"
            justifyContent="center">

            <h3>Edit Task</h3>
            </Box>
        <TextField
          style={{ width: "200px", margin: "10px" }}
          type="text"
          name="taskTitle"
          label="Title"
          value={taskTitle}
          onChange={handleInputChange}
          variant="outlined"
          />
        <br />
        <TextField
          style={{ width: "200px", margin: "10px" }}
          type="text"
          name="taskDescription"
          label="Description"
          value={taskDescription}
          onChange={handleInputChange}
          variant="outlined"
          />
        <br />
        <Box display="flex"
        alignContent="center"
        justifyContent="center"
        margin="2rem">

        <Button type="submit" variant="contained" color="primary">
          Edit
        </Button>
        </Box>
      </form>
          </Box>
        </>
    )
}

export default EditTask;
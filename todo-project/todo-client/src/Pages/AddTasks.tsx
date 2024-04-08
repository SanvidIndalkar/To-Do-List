import { AddTask, Label } from "@mui/icons-material";
import { Box, Button, Grid, MenuItem, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import { FormControl } from "@mui/base/FormControl"
import { Form, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const AddTasks = () => {
    
    const navigate = useNavigate();
    const queryClient = useQueryClient();

    const [task, setTask] = useState({
        taskTitle: "",
        taskDescription: "",
        taskStatus: "PENDING"
    })

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        console.log(name + " " + value)
        setTask((t) => ({
            ...t,
            [name] : value
        }))
    }

    const mutation = useMutation({
        mutationFn: () =>  axios.post("http://localhost:8080/tasks/add", task),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey : ["alltasks"]})
        navigate("/")
        }
    }
    )

    const handleSubmit = (e) => {
        e.preventDefault();
        mutation.mutate();
    }

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

            <h3>Add Task</h3>
            </Box>
        <TextField
          style={{ width: "200px", margin: "10px" }}
          type="text"
          name="taskTitle"
          label="Title"
          value={task.taskTitle}
          onChange={handleInputChange}
          variant="outlined"
          />
        <br />
        <TextField
          style={{ width: "200px", margin: "10px" }}
          type="text"
          name="taskDescription"
          label="Description"
          value={task.taskDescription}
          onChange={handleInputChange}
          variant="outlined"
          />
        <br />
        <Box display="flex"
        alignContent="center"
        justifyContent="center"
        margin="2rem">

        <Button type="submit" variant="contained" color="primary">
          Add
        </Button>
        </Box>
      </form>
          </Box>
        </>
    )
}


export default AddTasks;
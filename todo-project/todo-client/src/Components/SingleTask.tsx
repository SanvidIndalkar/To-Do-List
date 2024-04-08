import React, { Context, useEffect, useState } from "react";
import { Box, Checkbox, Grid, IconButton } from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { MutationFunction, useMutation, useQueryClient } from "@tanstack/react-query";
import { MutateFunction } from "@tanstack/react-query";


// -- need to figure out
// type Task = {
//     data: {
//         id: number, taskTitle: string; taskDescription: string;
//         taskStatus: "COMPLETED" | "PENDING" | "DELETED"
//     }
// }

// interface MutationFuncProps{
//     mutation: MutateFunction
// }

// interface MutationFuncProps1{
//     mutationChangeStatus : MutateFunction
// }



const SingleTask = ({ data, mutation, mutationChangeStatus }) => {

    const queryClient = useQueryClient();
    const navigate = useNavigate();
    // const { id, taskTitle, taskDescription, taskStatus } = task;


    useEffect(() => {
        if (mutationChangeStatus.isSuccess) {
            queryClient.invalidateQueries({ queryKey: ["alltasks"] });
            mutationChangeStatus.reset()
        }

        if (mutation.isSuccess) {
            queryClient.invalidateQueries({ queryKey: ["alltasks"] });
            mutation.reset()
        }
    }, [mutation, mutationChangeStatus, queryClient])

    //handling delete
    const handleDelete = () => {
        mutation.mutate(data.id)
    }

    //handling edit
    const handleEdit = () => {
        navigate("/edit", { state: data });
    }

    //handling checkbox changes
    const handleCheckBoxChange = () => {
        mutationChangeStatus.mutate(data.id);
    }

    return (
        <section>
            <Grid>
                <Box
                    display="flex"
                    alignContent="center"
                    justifyContent="space-between"
                    padding="0.1rem 1rem"
                    margin="0.5px"
                >
                    <Checkbox checked={data.taskStatus === "COMPLETED"}
                        onClick={handleCheckBoxChange}
                    />
                    <div>
                        <h3>{data.taskTitle}</h3>
                        <p>{data.taskDescription}</p>
                    </div>

                    <Box padding="1.4rem">
                        <IconButton>
                            <DeleteIcon onClick={handleDelete} />
                        </IconButton>
                        <IconButton>
                            <EditIcon onClick={handleEdit} />
                        </IconButton>
                    </Box>
                </Box>
            </Grid>

        </section>
    )
}

export default SingleTask;
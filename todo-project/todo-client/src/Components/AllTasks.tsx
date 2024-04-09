import React, { useEffect } from "react";


import SingleTask from "./SingleTask.tsx";
import { Box, Container, Grid } from "@mui/material";
import { QueryClient, UseQueryResult, useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import axios from "axios";

type TaskType = {
    id: number,
    taskTitle: string,
    taskDescription: string,
    taskStatus: "COMPLETED" | "DELETED" | "PENDING"
}

type ResponseData = {
    allTasks : TaskType[],
    length : number,
    success : boolean
    message : true
}

const AllTasks = () => {

    const queryClient = useQueryClient();

    //fetching all tasks
    const getAll : UseQueryResult<ResponseData, Error> = useQuery({
        queryKey: ["alltasks"],
        queryFn: async () => {
            try{
                const response = await axios.get("http://localhost:8080/tasks/all");
                console.log(response.data.data);
                return response.data.data;
            }
            catch(error){
                console.log("There is a error " + error)
                return error;
            }
        }
    })

    //mutation delete
    const mutation = useMutation({
        mutationFn: (id : number) => axios.delete("http://localhost:8080/tasks/delete/" + id),
    })

    //mutation changing status for completion or pending
    const mutationChangeStatus = useMutation({
        mutationFn: (id : number) => axios.put("http://localhost:8080/tasks/change-status/" + id),
    })


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

    const {data, isLoading, isError} = getAll;
    
    if(isLoading) return <div>Loading...</div>
    if(isError) return <div>Something is wrong...</div>
    // console.log("All tasks : " + data.allTasks);

    return (
        <>
        <Box
        margin="1rem 30rem 1rem 30rem">
            <Grid>
            {data?.allTasks?.map((task: TaskType) => 
                <SingleTask key = {task.id} data = {task} mutation = {mutation} mutationChangeStatus = {mutationChangeStatus}/>
            )}
            </Grid>
        </Box>
        
        </>
    )
}

export default AllTasks;
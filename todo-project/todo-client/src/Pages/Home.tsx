import React from "react";
import SingleTask from "../Components/SingleTask.tsx";
import AllTasks from "../Components/AllTasks.tsx";
import { Box, Card } from "@mui/material";
import { AddTask } from "@mui/icons-material";
import AddTasks from "./AddTasks.tsx";
import { Link } from "react-router-dom";

const Home = () => {

    return (
        <main>
            <Box
                display="flex"
                justifyContent="center"
                alignContent="center"
                margin="10rem 30rem 4rem 30rem"
                bgcolor="#CB8AFF"
            >
                <div>
                    <h2>Your To-Dos</h2>
                </div>
            </Box>

            <AllTasks />
            <Link to="/add">

                <Box bgcolor="#CB8AFF"
                    display="flex"
                    justifyContent="center"
                    alignContent="center"
                    margin="1rem 35rem 1rem 35rem"
                    padding="0.5rem 0.5rem"
                    columnGap="1rem">

                    <AddTask />
                        Add Task
                </Box>
            </Link>
        </main>
    )
}

export default Home;
import React from "react";
import Home from "./Pages/Home.tsx";
import { Box } from "@mui/material";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AddTask } from "@mui/icons-material";
import AddTasks from "./Pages/AddTasks.tsx";
import EditTask from "./Pages/EditTask.tsx";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/add" element={<AddTasks/>}/>
          <Route path="/edit" element={<EditTask/>}/>
        </Routes>
      </Router>
    </QueryClientProvider>
  );
}

export default App;

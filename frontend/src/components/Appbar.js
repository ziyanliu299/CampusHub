import MuiAppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

export default function Appbar({ username, role, onLogout }) {
  return (
    <MuiAppBar position="static">
      <Toolbar>
        <Typography variant="h6">CampusHub</Typography>

        <Box sx={{ flexGrow: 1 }} />

        <Typography variant="body2" sx={{ mr: 2 }}>
          {username ? `${username} (${role || "UNKNOWN"})` : "Not logged in"}
        </Typography>

        <Button color="inherit" onClick={onLogout}>
          Logout
        </Button>
      </Toolbar>
    </MuiAppBar>
  );
}
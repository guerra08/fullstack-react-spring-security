import { Alert } from "@mui/material";

function CustomAlert(props){

  return (
    <Alert severity={props.alertSeverity}>{props.alertContents}</Alert>
  )

}

export default CustomAlert;
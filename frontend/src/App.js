import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import NavComponent from "./NavComponent";
import ListComponent from "./ListComponent";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

export default function App() {
  const classes = useStyles();

  return (
    <>
      <NavComponent/>
      <ListComponent/>
    </>
  );
}
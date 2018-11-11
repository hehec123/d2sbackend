import React from 'react';
import PropTypes from 'prop-types';

// A skill component contains the skill name, id, and any skill dependencies
// as well as a customized form name. Its skill point value is passed to the Form component
const Skill = ({
  skill, formName, deps, value, handler,
}) => (
  <label data-toggle="tooltip" data-placement="top" title={deps} htmlFor="skill">
    {`${skill.name} (Level ${skill.level}+)`}
    <input type="number" className="skill form-control" name={formName} id="skill" min="0" max="20" value={value} onChange={handler} />
  </label>
);

Skill.propTypes = {
  skill: PropTypes.shape({
    name: PropTypes.string.isRequired,
    level: PropTypes.number.isRequired,
  }).isRequired,
  formName: PropTypes.string.isRequired,
  value: PropTypes.number.isRequired,
  deps: PropTypes.string.isRequired,
  handler: PropTypes.func.isRequired,
};

export default Skill;
